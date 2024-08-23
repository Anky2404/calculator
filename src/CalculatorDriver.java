import javax.swing.*;

/*
 * Driver Class for the Calculator
 */
public class CalculatorDriver {

   /**
    * @param args the command line arguments
    */
   public static void main(String[] args) {
      try {
         for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
               UIManager.setLookAndFeel(info.getClassName());
               break;
            }
         }
      } catch (ClassNotFoundException | InstantiationException
               | IllegalAccessException | UnsupportedLookAndFeelException ex) {
         java.util.logging.Logger.getLogger(CalculatorDriver.class
                 .getName()).log(java.util.logging.Level.SEVERE, null, ex);
      }
      JFrame frame = new JFrame("Anky Calculator");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      CalculatorPanel panel = new CalculatorPanel();
      frame.getContentPane().add(panel);


      frame.pack();
      frame.setResizable(false);
      frame.setLocation(800, 300);
      frame.setVisible(true);
   }
}
