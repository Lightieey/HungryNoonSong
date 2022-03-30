import java.awt.*;

import javax.swing.JButton;

public class ButtonChange extends JButton {
    public ButtonChange(String text) { 
  	  super(text); 
  	  decorate(); 
    }
    
    protected void decorate() { // ��ư �ʱⰪ �ʱ�ȭ
  	  setBorderPainted(false); 
  	  setOpaque(false); 
    } 
   
    @Override 
    protected void paintComponent(Graphics g) {
  	 Color fontcolor = new Color(249,247,248);
  	 Color bc = new Color(161,182,215);
       
       Graphics2D graphics = (Graphics2D) g; 
       int w = getWidth(); 
       int h = getHeight(); 
       
       graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
   
       if (getModel().isArmed()) { // ��ư�� ������ ��Ӱ�
      	 graphics.setColor(bc.darker());
       } else if (getModel().isRollover()) { // ��ư ���� �ø��� ���
      	 graphics.setColor(bc.brighter()); 
       } else { 
      	 graphics.setColor(bc); 
       }

       graphics.fillRoundRect(0, 0, w, h, 10, 10);
      
       FontMetrics fontMetrics = graphics.getFontMetrics(); // ���� ������ ��Ʈ�� ���� ����
       Rectangle stringBounds = fontMetrics.getStringBounds(this.getText(), graphics).getBounds(); // ���� 
       
       int textX = (w - stringBounds.width) / 2; // �ؽ�Ʈ�� x�� ��ġ ����
       int textY = (h - stringBounds.height) / 2 + fontMetrics.getAscent(); // �ؽ�Ʈ�� y�� ��ġ ����
       
       graphics.setColor(fontcolor); // �� ����
       graphics.drawString(getText(), textX, textY); // �ؽ�Ʈ ��ġ
       graphics.dispose(); 
       
       super.paintComponent(g); 
     }
}