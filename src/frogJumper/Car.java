package frogJumper;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Car extends JLabel implements Runnable {
	ImageIcon ImgLeft = new ImageIcon(this.getClass().getResource("/carLeft.png"));
	ImageIcon ImgRight = new ImageIcon(this.getClass().getResource("/carRight.png"));
	ImageIcon ImgLeft2 = new ImageIcon(this.getClass().getResource("/carLeft2.png"));
	ImageIcon ImgRight2 = new ImageIcon(this.getClass().getResource("/carRight2.png"));
	int width = 226;
	int height = 100;
	Thread moveCar;
	static int difficulty=0;

	public Car() {
		super();
		moveCar = new Thread(this);
		difficulty=0;
	}

	public void SetLeft() {
		setIcon(ImgLeft);
	}

	public void SetLeft2() {
		setIcon(ImgLeft2);
	}

	public void SetRight() {
		setIcon(ImgRight);
	}

	public void SetRight2() {
		setIcon(ImgRight2);
	}

	boolean moveRight = true;

	@Override
	public void run() {
		int go = 0;
		while (Frog.alive) {
			if (go == 100)
				go = 0;
			try {
				Thread.sleep(15-difficulty);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (getX() > MainFrame.f.getWidth() - (width - 70)) {
				moveRight = false;
				MainFrame.playSound("car.wav");
			}
			if (getX() < -70) {
				moveRight = true;
				MainFrame.playSound("car.wav");
			}

			if (moveRight) {
				setBounds(getX() + 4, getY(), width, height);
				if (go % 2 == 0)
					SetRight();
				else
					SetRight2();
			} else {
				setBounds(getX() - 4, getY(), width, height);
				if (go % 2 == 0)
					SetLeft();
				else
					SetLeft2();
			}
			if (checkCollision()) {
				System.out.println("Marr gayaaaa!!");
				Frog.alive = false;
				MainFrame.frog.Dead();
				MainFrame.playSound("frogKill.wav");
			}
			go++;
		}
	}

	public boolean checkCollision() {
		for (int x = MainFrame.frog.getX(); x < MainFrame.frog.getX() + MainFrame.frog.width; x++) {
			for (int y = MainFrame.frog.getY(); y < MainFrame.frog.getY() + MainFrame.frog.height; y++) {
				if ((getX() + 40 == x && getY() + height / 2 == y)
						|| (getX() - 40 + width == x && getY() + height / 2 == y)) {
					return true;
				}
			}
		}
		return false;
	}
}
