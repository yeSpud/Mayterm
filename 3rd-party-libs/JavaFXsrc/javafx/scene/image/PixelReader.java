/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package javafx.scene.image;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import javafx.scene.paint.Color;

/**
 * This interface defines methods for retrieving the pixel data from an
 * {@link Image} or other surface containing pixels.
 * @since JavaFX 2.2
 */
public interface PixelReader {
    /**
     * This method returns the {@code PixelFormat} in which the surface
     * stores its pixels, or a roughly equivalent pixel format into which
     * it can easily convert its pixels for purposes of reading them.
     *
     * @return the {@code PixelFormat} that best describes the underlying
     *         pixels
     */
    public PixelFormat getPixelFormat();

    /**
     * Reads a 32-bit integer representation of the color of a pixel
     * from the specified coordinates in the surface.
     * The 32-bit integer will contain the 4 color components in separate
     * 8-bit fields in ARGB order from the most significant byte to the least
     * significant byte.
     *
     * @param x the X coordinate of the pixel color to read
     * @param y the Y coordinate of the pixel color to read
     * @return a 32-bit representation of the color in the format
     *         described by the {@link PixelFormat.Type#INT_ARGB INT_ARGB}
     *         PixelFormat type.
     */
    public int getArgb(int x, int y);

    /**
     * Reads the color of a pixel from the specified coordinates in the
     * surface and returns the value as a {@link Color} object.
     *
     * @param x the X coordinate of the pixel color to read
     * @param y the Y coordinate of the pixel color to read
     * @return the Color object representing the color of the indicated
     *         pixel
     */
    public Color getColor(int x, int y);

    /**
     * Reads pixel data from a rectangular region of the surface into the
     * specified buffer.
     * The format to be used for pixels in the buffer is defined by the
     * {@link PixelFormat} object and pixel format conversions will be
     * performed as needed to store the data in the indicated format.
     * The buffer is assumed to be positioned to the location where the
     * first pixel data from the image pixel at location {@code (x, y)}
     * will be stored.
     * Pixel data for a row will be stored in adjacent locations within
     * the buffer packed as tightly as possible for increasing X
     * coordinates.
     * Pixel data for adjacent rows will be stored offset from each other
     * by the number of buffer data elements defined by
     * {@code scanlineStride}.
     *
     * @param x the X coordinate of the rectangular region to read
     * @param y the Y coordinate of the rectangular region to read
     * @param w the width of the rectangular region to read
     * @param h the height of the rectangular region to read
     * @param pixelformat the {@code PixelFormat} object defining the format
     *        to store the pixels into buffer
     * @param buffer a buffer of a type appropriate for the indicated
     *        {@code PixelFormat} object
     * @param scanlineStride the distance between the pixel data for the
     *        start of one row of data in the buffer to the start of the
     *        next row of data.
     */
    public <T extends Buffer>
        void getPixels(int x, int y, int w, int h,
                       WritablePixelFormat<T> pixelformat,
                       T buffer, int scanlineStride);

    /**
     * Reads pixel data from a rectangular region of the surface into the
     * specified byte array.
     * The format to be used for pixels in the buffer is defined by the
     * {@link PixelFormat} object and pixel format conversions will be
     * performed as needed to store the data in the indicated format.
     * The {@code pixelformat} must be a compatible
     * {@code PixelFormat<ByteBuffer>} type.
     * The data for the first pixel at location {@code (x, y)} will be
     * read into the array index specified by the {@code offset} parameter.
     * Pixel data for a row will be stored in adjacent locations within
     * the array packed as tightly as possible for increasing X
     * coordinates.
     * Pixel data for adjacent rows will be stored offset from each other
     * by the number of byte array elements defined by
     * {@code scanlineStride}.
     *
     * @param x the X coordinate of the rectangular region to read
     * @param y the Y coordinate of the rectangular region to read
     * @param w the width of the rectangular region to read
     * @param h the height of the rectangular region to read
     * @param pixelformat the {@code PixelFormat<ByteBuffer>} object
     *        defining the byte format to store the pixels into buffer
     * @param buffer a byte array to store the returned pixel data
     * @param offset the offset into {@code buffer} to store the first
     *        pixel data
     * @param scanlineStride the distance between the pixel data for the
     *        start of one row of data in the buffer to the start of the
     *        next row of data
     */
    public void getPixels(int x, int y, int w, int h,
                          WritablePixelFormat<ByteBuffer> pixelformat,
                          byte buffer[], int offset, int scanlineStride);

    /**
     * Reads pixel data from a rectangular region of the surface into the
     * specified int array.
     * The format to be used for pixels in the buffer is defined by the
     * {@link PixelFormat} object and pixel format conversions will be
     * performed as needed to store the data in the indicated format.
     * The {@code pixelformat} must be a compatible
     * {@code PixelFormat<IntBuffer>} type.
     * The data for the first pixel at location {@code (x, y)} will be
     * read into the array index specified by the {@code offset} parameter.
     * Pixel data for a row will be stored in adjacent locations within
     * the array packed as tightly as possible for increasing X
     * coordinates.
     * Pixel data for adjacent rows will be stored offset from each other
     * by the number of int array elements defined by
     * {@code scanlineStride}.
     *
     * @param x the X coordinate of the rectangular region to read
     * @param y the Y coordinate of the rectangular region to read
     * @param w the width of the rectangular region to read
     * @param h the height of the rectangular region to read
     * @param pixelformat the {@code PixelFormat<IntBuffer>} object
     *        defining the int format to store the pixels into buffer
     * @param buffer a int array to store the returned pixel data
     * @param offset the offset into {@code buffer} to store the first
     *        pixel data
     * @param scanlineStride the distance between the pixel data for the
     *        start of one row of data in the buffer to the start of the
     *        next row of data
     */
    public void getPixels(int x, int y, int w, int h,
                          WritablePixelFormat<IntBuffer> pixelformat,
                          int buffer[], int offset, int scanlineStride);
}
