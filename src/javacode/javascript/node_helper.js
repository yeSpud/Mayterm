// https://github.com/caseif/vis.js/

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