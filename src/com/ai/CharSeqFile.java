package com.ai;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;


/**
 * This class is intended to act as an adapter Class.
 * The adaptee being an InputStream object.
 * The point is to adapt an InputStream to behave like
 * a char sequence. 
 * @author Martin Tice
 *
 */
public class CharSeqFile implements CharSequence {

	/**{@link FileInputStream} to be "adapted to a CharSeqFile"*/
	private FileInputStream _inputStream;
	/**size of {@link #fc}*/
	private int _size;
	/**step between {FileInputStream} and {@link #_inMBB}*/
	private final FileChannel fc;
	/**form {@link #_inputStream} is turned into just before final {@link CharSeqFile} output*/
	private MappedByteBuffer _inMBB;
	
	/**
	 * Constructor takes a filename and:
	 * <ul>
	 * <li>
	 * turns file into FileInputStream object {@link #_inputStream}
	 * </li>
	 * <li>
	 * turns {@link #_inputStream} into @Link FileChannel object {@link #fc}
	 * </li>
	 * <li>
	 * turns {@link #fc} into @Link MappedByteBuffer object {@link #_inMBB}
	 * that is read_only, starts at beginning, and is size of {@link #fc}
	 * </li>
	 * </ul>
	 * @param fileName filename of input file
	 *  
	 */
	public CharSeqFile (String fileName) throws IOException{
		
		_inputStream = new FileInputStream(fileName);
		fc = _inputStream.getChannel();
		
		_size = (int) fc.size();
		_inMBB = fc.map(FileChannel.MapMode.READ_ONLY,0L,_size);

	}
	
	//---------------CharSequence methods to adapt to use on an input stream
	
	/**
	 * Returns a char at a given index in the @link MappedByteBuffer object {@link #_inMBB}
	 * 
	 * @param pos the position for which to return a char in the {@link #_inMBB}
	 * @return char The char at the position pos
	 */
	public char charAt(int pos) {
		return (char)_inMBB.get(pos);
	}

	/**
	 * Returns the size of the @link FileChannel object created from the {@link #_inputStream}
	 * @return int size of the @link FileChannel object created from the {@link #_inputStream}
	 */
	public int length() {
		return _size;
	}

	/**
	 * Builds a CharSequence from the {@link #_inMBB} between a given set of indices. 
	 * Does this by creating a {@link StringBuilder} object and appending requested chars from {@link #_inMBB} to it.
	 * 
	 * @param start is the starting index of subSequence
	 * @param end is the ending index of subSequence.
	 * @return a subSequence of characters between given indices. 
	 */
	public CharSequence subSequence(int start, int end) {
		StringBuilder sub = new StringBuilder("");
		try{
		for(int i = start; i<end;i++){
		sub.append((char) _inMBB.get(i));
		}
		}catch(IndexOutOfBoundsException e){
			System.out.println("CharSeqFile.charSequence indexOutOfBounds");
		}
		return sub;
		}
}

