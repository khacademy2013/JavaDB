package me.thumbnail;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
// Thumbnailator 라이브러리를 이용한 섬네일  
class ThumbnailsTest2 extends JFrame{
 	private static final long serialVersionUID = 523285448953446819L;
	private Image img = null;
    public ThumbnailsTest2(){
    	super("이미지 그리기");
        try {
        	// 160 * 160 섬네일 이미지 생성 "Duke.gif"를 오른쪽아래에 워터마크 효과줌
        	Thumbnails.of(new File("java.jpg"))
            .size(160, 160)
            .rotate(90)
            .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File("Duke.gif")), 0.5f)
            .outputQuality(0.8)
            .toFile(new File("image-with-watermark.jpg"));		
        } catch (IOException e) {
			e.printStackTrace();
		}
        
        img = Toolkit.getDefaultToolkit().getImage("image-with-watermark.jpg");
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
            g.drawImage(img, 50, 50, this);
        }
    }
    public static void main(String[] args){
        new ThumbnailsTest2();
    }
}
