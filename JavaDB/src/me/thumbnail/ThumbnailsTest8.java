package me.thumbnail;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;
// Thumbnailator 라이브러리를 이용한 섬네일  
class ThumbnailsTest8 extends JFrame{
 	private static final long serialVersionUID = 523285448953446819L;
	BufferedImage watermarkImage = null;

    public ThumbnailsTest8(){
    	super("이미지 그리기");
        try {
        	// 출력 디렉토리 지정
         	File destinationDir = new File("thumbnail");
        	// 접두어를 붙여서 일괄 저장
        	Thumbnails.of("images/Duke1.gif", "images/Duke2.gif", "images/Duke3.gif")
        	        .size(200, 200)
        	        .toFiles(destinationDir, Rename.PREFIX_DOT_THUMBNAIL);  
           	// 접두어없이 일괄 저장
        	Thumbnails.of("images/Duke1.gif", "images/Duke2.gif", "images/Duke3.gif")
        	        .size(200, 200)
        	        .toFiles(destinationDir, Rename.NO_CHANGE);  
           	// 접미사를 붙여서 일괄 저장
        	Thumbnails.of("images/Duke1.gif", "images/Duke2.gif", "images/Duke3.gif")
        	        .size(200, 200)
        	        .toFiles(destinationDir, Rename.SUFFIX_HYPHEN_THUMBNAIL);  
        	
        } catch (IOException e) {
			e.printStackTrace();
		}

        setSize(420, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public static void main(String[] args){
        new ThumbnailsTest8();
    }
}
