package frogJumper;

import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Fly extends JLabel implements Runnable {
	ImageIcon flyImg = new ImageIcon(this.getClass().getResource("/fly.png"));
	ImageIcon flySupImg = new ImageIcon(this.getClass().getResource("/flyS.png"));
	Random random = new Random();
	int width = 50;
	int height = 50;
	Thread StartFly;
	int flies = 0;
	boolean Sup;
	static int go=0;
	public Fly(boolean Sup) {
		super();
		if (!Sup)
			setIcon(flyImg);
		else
			setIcon(flySupImg);
		StartFly = new Thread(this);
		this.Sup = Sup;
		go=0;
	}

	public void setPosUp() {
		setBounds(random.nextInt(MainFrame.f.getWidth() - 10 - width) + 10, 75, width, height);
	}

	public void setPosDown() {
		setBounds(random.nextInt(MainFrame.f.getWidth() - 10 - width) + 10, MainFrame.f.getHeight() - 70 - height,
				width, height);
	}

	public void setPosMid() {
		setBounds(random.nextInt(MainFrame.f.getWidth() - 10 - width) + 10, MainFrame.car.getY() + height / 2, width,
				height);
	}

	public boolean checkCollision() {// check collision with fly
		for (int x = MainFrame.frog.getX(); x < MainFrame.frog.getX() + MainFrame.frog.width; x++) {
			for (int y = MainFrame.frog.getY(); y < MainFrame.frog.getY() + MainFrame.frog.height; y++) {
				if ((getX() + width / 2 == x && getY() + height / 2 == y)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void run() {
		while (Frog.alive) {
			if (go == 100)
				go = 0;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
			if (checkCollision()) {
				if (!Sup) {
					if (go % 2 == 0) {
						setPosDown();
					} else {
						setPosUp();
					}
					MainFrame.score += 10;
					flies++;
					if (flies % 3 == 0)
						Car.difficulty++;
					if((flies % 5 == 0)){
						MainFrame.flySup.setPosMid();
					}
					go++;
					MainFrame.playSound("eat.wav");
				} else {
					setBounds(-80,-80,width,height);
					MainFrame.score += 50;
					MainFrame.playSound("eat2.wav");
				}
				MainFrame.Score.setText("Score: " + MainFrame.score + "  Hi: " + MainFrame.Hiscore);
			}
			
		}

	}
}
