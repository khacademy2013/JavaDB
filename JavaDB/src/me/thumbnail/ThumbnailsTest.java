package me.thumbnail;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import net.coobird.thumbnailator.Thumbnails;
// Thumbnailator 라이브러리를 이용한 섬네일  
class ThumbnailsTest extends JFrame{
 	private static final long serialVersionUID = 523285448953446819L;
	private Image img = null;
    public ThumbnailsTest(){
    	super("이미지 그리기");
        try {
        	// 160 * 160 섬네일 이미지 생성
			Thumbnails.of(new File("java.jpg")).size(160, 160).toFile(new File("thumbnail.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        img = Toolkit.getDefaultToolkit().getImage("thumbnail.jpg");
        MediaTracker tracker = new MediaTracker(this);
        tracker.addImage(img, 0);
        try{
           tracker.waitForAll();
        }catch(InterruptedException e){
            e.printStackTrace();
        }        

        setSize(420, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public void paint(Graphics g){
        if(this.img != null){
            g.drawImage(img,50, 50, this);
        }
    }
    public static void main(String[] args){
        new ThumbnailsTest();
    }
}
