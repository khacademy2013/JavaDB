package me.thumbnail;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import net.coobird.thumbnailator.Thumbnails;
// Thumbnailator 라이브러리를 이용한 섬네일  
class ThumbnailsTest4 extends JFrame{
 	private static final long serialVersionUID = 523285448953446819L;
 	private BufferedImage originalImage = null;
	private BufferedImage thumbnail = null;
    public ThumbnailsTest4(){
    	super("이미지 그리기");
        try {
        	// 200 *  200 섬네일 이미지를 BufferedImage로 만듬
        	originalImage = ImageIO.read(new File("java.png"));
        	thumbnail = Thumbnails.of(originalImage).size(200, 200).asBufferedImage();       	
        } catch (IOException e) {
			e.printStackTrace();
		}

        setSize(420, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public void paint(Graphics g){
        if(this.originalImage != null && this.thumbnail != null){
            g.drawImage(originalImage, 50, 50, this);
            g.drawImage(thumbnail, 50, 200, this);
        }
    }
    public static void main(String[] args){
        new ThumbnailsTest4();
    }
}
