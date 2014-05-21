package me.thumbnail;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JFrame;

import net.coobird.thumbnailator.Thumbnails;
// Thumbnailator 라이브러리를 이용한 섬네일  
class ThumbnailsTest3 extends JFrame{
 	private static final long serialVersionUID = 523285448953446819L;
	private Image img = null;
    public ThumbnailsTest3(){
    	super("이미지 그리기");
    	OutputStream os = null;
        try {
        	// 160 * 160 섬네일 이미지 생성해서 파일로 저장
        	os = new FileOutputStream("thumbnail2.png") ;
        	Thumbnails.of("java.jpg").size(200, 200).outputFormat("png").toOutputStream(os);
        	
        } catch (IOException e) {
			e.printStackTrace();
		} finally{
			try { if(os!=null) os.close();}catch(IOException e){ ; }
		}
        
        img = Toolkit.getDefaultToolkit().getImage("thumbnail2.png");
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
        new ThumbnailsTest3();
    }
}
