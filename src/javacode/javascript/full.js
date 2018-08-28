/* https://github.com/caseif/vis.js/ */ /* spectrum_algorithm.js */ /* mostly for debugging purposes */ 
function smooth(array) {return savitskyGolaySmooth(array);}
function savitskyGolaySmooth(array) {var lastArray = array;for (var pass = 0; pass < smoothingPasses; pass++) {var sidePoints = Math.floor(smoothingPoints / 2); /* our window is centered so this is both nL and nR */ var cn = 1 / (2 * sidePoints + 1); /* constant */ var newArr = [];for (var i = 0; i < sidePoints; i++) {newArr[i] = lastArray[i];newArr[lastArray.length - i - 1] = lastArray[lastArray.length - i - 1];}for (var i = sidePoints; i < lastArray.length - sidePoints; i++) {var sum = 0;for (var n = -sidePoints; n <= sidePoints; n++) {sum += cn * lastArray[i + n] + n;}newArr[i] = sum;}lastArray = newArr;}return newArr;}
function transformToVisualBins(array) {var newArray = new Uint8Array(spectrumSize);for (var i = 0; i < spectrumSize; i++) {var bin = Math.pow(i / spectrumSize, spectrumScale) * (spectrumEnd - spectrumStart) + spectrumStart;newArray[i] = array[Math.floor(bin) + spectrumStart] * (bin % 1) + array[Math.floor(bin + 1) + spectrumStart] * (1 - (bin % 1))}return newArray;}
function getTransformedSpectrum(array) {var newArr = normalizeAmplitude(array);newArr = averageTransform(newArr);newArr = tailTransform(newArr);newArr = smooth(newArr);newArr = exponentialTransform(newArr);return newArr;}
function normalizeAmplitude(array) {var values = [];for (var i = 0; i < spectrumSize; i++) {if (begun) {values[i] = array[i] / 255 * spectrumHeight;} else {value = 1;}}return values;}
function averageTransform(array) {
	var values = [];
	var length = array.length;

	for (var i = 0; i < length; i++) {
		var value = 0;
		if (i == 0) {
			value = array[i];
		} else if (i == length - 1) {
			value = (array[i - 1] + array[i]) / 2;
		} else {
			var prevValue = array[i - 1];
			var curValue = array[i];
			var nextValue = array[i + 1];

			if (curValue >= prevValue && curValue >= nextValue) {
				value = curValue;
			} else {
				value = (curValue + Math.max(nextValue, prevValue)) / 2;
			}
		}
		value = Math.min(value + 1, spectrumHeight);

		values[i] = value;
	}

	var newValues = [];
	for (var i = 0; i < length; i++) {
		var value = 0;
		if (i == 0) {
			value = values[i];
		} else if (i == length - 1) {
			value = (values[i - 1] + values[i]) / 2;
		} else {
			var prevValue = values[i - 1];
			var curValue = values[i];
			var nextValue = values[i + 1];

			if (curValue >= prevValue && curValue >= nextValue) {
				value = curValue;
			} else {
				value = ((curValue / 2) + (Math.max(nextValue, prevValue) / 3) + (Math.min(nextValue, prevValue) / 6));
			}
		}
		value = Math.min(value + 1, spectrumHeight);

		newValues[i] = value;
	}
	return newValues;
}

function tailTransform(array) {
	var values = [];
	for (var i = 0; i < spectrumSize; i++) {
		var value = array[i];
		if (i < headMargin) {
			value *= headMarginSlope * Math.pow(i + 1, marginDecay) + minMarginWeight;
		} else if (spectrumSize - i <= tailMargin) {
			value *= tailMarginSlope * Math.pow(spectrumSize - i, marginDecay) + minMarginWeight;
		}
		values[i] = value;
	}
	return values;
}

function exponentialTransform(array) {
	var newArr = [];
	for (var i = 0; i < array.length; i++) {
		var exp = (spectrumMaxExponent - spectrumMinExponent) * (1 - Math.pow(i / spectrumSize, spectrumExponentScale)) + spectrumMinExponent;
		newArr[i] = Math.max(Math.pow(array[i] / spectrumHeight, exp) * spectrumHeight, 1);
	}
	return newArr;
}

// top secret bleeding-edge shit in here
function experimentalTransform(array) {
    var resistance = 3; // magic constant
    var newArr = [];
    for (var i = 0; i < array.length; i++) {
    	var sum = 0;
    	var divisor = 0;
    	for (var j = 0; j < array.length; j++) {
    		var dist = Math.abs(i - j);
    		var weight = 1 / Math.pow(2, dist);
    		if (weight == 1) weight = resistance;
    		sum += array[j] * weight;
    		divisor += weight;
    	}
    	newArr[i] = sum / divisor;
    }
    return newArr;
}

// node_helper.js

var context = new AudioContext();
var dispContext = new AudioContext();
var gainNode;
var audioBuffer;
var bufferSource;
var dispBufferSource;
var analyzer;
var dispScriptProcessor;
var scriptProcessor;
var bufferInterval = 1024;

function setupAudioNodes() {
	bufferSource = context.createBufferSource();
	bufferSource.connect(context.destination);

	muteGainNode = context.createGain();
	muteGainNode.gain.value = -1;
	bufferSource.connect(muteGainNode);
	muteGainNode.connect(context.destination);

	gainNode = context.createGain();
	gainNode.gain.value = 0;

	delayNode = context.createDelay(1);
	delayNode.delayTime.value = audioDelay;
	bufferSource.connect(gainNode);
	gainNode.connect(delayNode);
	bufferSource.connect(delayNode);
	delayNode.connect(context.destination);

	scriptProcessor = context.createScriptProcessor(bufferInterval, 1, 1);
	scriptProcessor.connect(context.destination);

	analyzer = context.createAnalyser();
	analyzer.connect(scriptProcessor);
	analyzer.smoothingTimeConstant = temporalSmoothing;
	analyzer.minDecibels = -100;
	analyzer.maxDecibels = -33;
	try {
        analyzer.fftSize = maxFftSize; // ideal bin count
        print('Using fftSize of ' + analyzer.fftSize + ' (woot!)');
    } catch (ex) {
        analyzer.fftSize = 2048; // this will work for most if not all systems
        print('Using fftSize of ' + analyzer.fftSize);
        print('Could not set optimal fftSize! This may look a bit weird...');
    }
    bufferSource.connect(analyzer);
}

function playSound(buffer) {
	bufferSource.buffer = buffer;
	bufferSource.start(0);
	isPlaying = true;
	begun = true;
	started = Date.now();
}

// config.js
// NOTE: Not all config values may necessarily be changed by the user at
// runtime. Some are mutated internally after intialization, meaning changing
// them at runtime may not work as expected.

/* *********************** */
/* * Audio node settings * */
/* *********************** */
var volumeStep = 0.05; // the step for each volume notch as a fraction of 1

/* *************************** */
/* * Basic spectrum settings * */
/* *************************** */
// BASIC ATTRIBUTES
var spectrumSize = 63; // number of bars in the spectrum
var spectrumDimensionScalar = 4.5; // the ratio of the spectrum width to its height
var spectrumSpacing = 7; // the separation of each spectrum bar in pixels at width=1920
var maxFftSize = 16384; // the preferred fftSize to use for the audio node (actual fftSize may be lower)
var audioDelay = 0.4; // audio will lag behind the rendered spectrum by this amount of time (in seconds)
// BASIC TRANSFORMATION
var spectrumStart = 4; // the first bin rendered in the spectrum
var spectrumEnd = 1200; // the last bin rendered in the spectrum
var spectrumScale = 2.5; // the logarithmic scale to adjust spectrum values to
// EXPONENTIAL TRANSFORMATION
var spectrumMaxExponent = 6; // the max exponent to raise spectrum values to
var spectrumMinExponent = 3; // the min exponent to raise spectrum values to
var spectrumExponentScale = 2; // the scale for spectrum exponents
// DROP SHADOW
var spectrumShadowBlur = 6; // the blur radius of the spectrum's drop shadow
var spectrumShadowOffsetX = 0; // the x-offset of the spectrum's drop shadow
var spectrumShadowOffsetY = 0; // the y-offset of the spectrum's drop shadow

/* ********************** */
/* * Smoothing settings * */
/* ********************** */
var smoothingPoints = 3; // points to use for algorithmic smoothing. Must be an odd number.
var smoothingPasses = 1; // number of smoothing passes to execute
var temporalSmoothing = 0.2; // passed directly to the JS analyzer node

/* ************************************ */
/* * Spectrum margin dropoff settings * */
/* ************************************ */
var headMargin = 7; // the size of the head margin dropoff zone
var tailMargin = 0; // the size of the tail margin dropoff zone
var minMarginWeight = 0.7; // the minimum weight applied to bars in the dropoff zone

/* *************************** */
/* * Basic particle settings * */
/* *************************** */
// COUNT
var baseParticleCount = 2000; // the number of particles at 1080p
var fleckCount = 50; // total fleck count
var bokehCount = 250; // total bokeh count
// OPACITY
var particleOpacity = 0.7; // opacity of primary particles
var bokehOpacity = 0.5; // opacity of bokeh (raising this above 0.5 results in weird behavior)
// SIZE
var minParticleSize = 4; // the minimum size scalar for particle systems
var maxParticleSize = 7; // the maximum size scalar for particle systems
var particleSizeExponent = 2; // the exponent to apply during dynamic particle scaling (similar to spectrum exponents)
// POSITIONING
var yVelRange = 3; // the range for particle y-velocities
var xPosBias = 4.5; // bias for particle x-positions (higher values = more center-biased)
var zPosRange = 450; // the range of z-particles
var zModifier = -250; // the amount to add to z-positions
var zPosBias = 2.3; // bias for particle z-positions (higher values = more far-biased)
var leftChance = 0.88; // the chance for a particle to spawn along the left edge of the screen
var rightChance = 0.03; // the chance for a particle to spawn along the right edge of the screen
var topBottomChance = 0.09; // the chance for a particle to spawn along the top/bottom edges of the screen
// VELOCITY
var velBias = 1.8; // bias for particle velocities (higher values = more center-biased)
var minParticleVelocity = 2; // the minimum scalar for particle velocity
var maxParticleVelocity = 5; // the maximum scalar for particle velocity
var absMinParticleVelocity = 0.001; // the absolute lowest speed for particles
var fleckVelocityScalar = 1.75; // velocity of flecks relative to normal particles
var fleckYVelScalar = 0.75; // y-velocity range of flecks relative to x-velocity
var bokehMinVelocity = maxParticleVelocity * 0.15; // the minimum velocity of bokeh
var bokehMaxVelocity = maxParticleVelocity * 0.3; // the maximum velocity of bokeh

/* ****************************** */
/* * Particle analysis settings * */
/* ****************************** */
var ampLower = 7; // the lower bound for amplitude analysis (inclusive)
var ampUpper = 30; // the upper bound for amplitude analysis (exclusive)
var particleExponent = 4.5; // the power to raise velMult to after initial computation

/* ***************** */
/* * Misc settings * */
/* ***************** */
var cycleSpeed = 4; // the (arbitrary) scalar for cycling rainbow spectrums
var blockWidthRatio = 0.63; // the width of the Monstercat logo relative to its containing block
var blockHeightRatio = 0.73; // the height of the Monstercat logo relative to its containing block
var mouseSleepTime = 1000; // inactivity period in milliseconds before the bottom text is hidden

// spectrum.js
var lastProcess = Date.now();
var lastSpectrum = [];
var prevPeak = -1;

function initSpectrumHandler() {scriptProcessor.onaudioprocess = handleAudio;}

function handleAudio() {
    // don't do anything if the audio is paused
    if (!isPlaying) {
    	return;
    }

    var now = Date.now();
    do { 
    	now = Date.now(); 
    } while (now - lastProcess < minProcessPeriod);
    lastProcess = Date.now();

    var initialArray =  new Uint8Array(analyzer.frequencyBinCount);
    analyzer.getByteFrequencyData(initialArray);
    var array = transformToVisualBins(initialArray);


    drawSpectrum(array);
}

var spectrumAnimation = "phase_1";
var spectrumAnimationStart = 0;

function drawSpectrum(array) {
	if (isPlaying) {
		if (lastSpectrum.length == 1) {
			lastSpectrum = array;
		}
	}

	var drawArray = isPlaying ? array : lastSpectrum;
	array = getTransformedSpectrum(array);

	var now = Date.now();

	if (spectrumAnimation == "phase_1") {
		var ratio = (now - started) / 500;

		if (ratio > 1) {
			spectrumAnimation = "phase_2";
			spectrumAnimationStart = now;
		}
	} else if (spectrumAnimation == "phase_2") {
		var ratio = (now - spectrumAnimationStart) / 500;

		if (ratio > 1) {
			spectrumAnimation = "phase_3";
			spectrumAnimationStart = now;
		}
	} else if (spectrumAnimation == "phase_3") {
		var ratio = (now - spectrumAnimationStart) / 1000;

        // drawing pass
        for (var i = 0; i < spectrumSize; i++) {
        	var value = array[i];

            // Used to smooth transiton between bar & full spectrum (lasts 1 sec)
            if (ratio < 1) {
            	value = value / (1 + 9 - 9 * ratio); 
            }

            if (value < 2 * resRatio) {
            	value = 2 * resRatio;
            }
        }
    }
}

function updateParticleAttributes(array) {var sum = 0;for (var i = ampLower; i < ampUpper; i++) {sum += array[i] / spectrumHeight;}velMult = sum / (ampUpper - ampLower);particleSize = velMult;velMult = Math.pow(velMult, particleExponent) * (1 - absMinParticleVelocity) + absMinParticleVelocity;particleSize = (maxParticleSize - minParticleSize) * Math.pow(particleSize, particleSizeExponent) + minParticleSize;}