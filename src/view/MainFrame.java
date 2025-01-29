package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame implements ActionListener{

    //private static final long serialVersionUID = 1L;
    private JPanel panel;
    private JPanel panel2;
    private JPanel panel3;

    public MainFrame() {
        super("Gestore di Prenotazioni");
        this.setSize(1000, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        MenuPanel menu =new MenuPanel();

        menu.setTabellaButtonListener(this);
        
    }

    public void switchPanel(JPanel panel){
        this.removeAll();
        this.add(panel);
        this.revalidate();
        this.repaint();
    }

    public void menuPanel(){

    }
    

    public void panel2(){
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'panel2'");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

    
    
    

}
