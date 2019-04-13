import Transcode.VLCTranscode;

import javax.swing.*;
import java.util.Objects;

public class MyForm extends JFrame {
    private JPanel rootPanel;
    private JButton fileButton;
    private JTextArea textArea1;
    private JButton buttonTranscode;
    private JComboBox comboBoxVideo;
    private JComboBox comboBoxAudio;
    private JLabel JLabelFile;
    private JSpinner spinnerVideo;
    private JSpinner spinnerAudio;
    private JSpinner spinnerScale;
    private JSpinner spinnerWidth;
    private JSpinner spinnerHeight;
    private VLCTranscode transcoder;
    private String toTranscode = "";

    public MyForm() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        spinnerAudio.setValue(98);
        spinnerVideo.setValue(1265);
        spinnerScale.setValue(23.976000);
        spinnerHeight.setValue(360);
        spinnerWidth.setValue(640);
        this.add(rootPanel);
        this.setSize(600,400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        //////////////
        VLCTranscode.Vcodec[] Vcodecs = VLCTranscode.Vcodec.values();
        for (VLCTranscode.Vcodec codec: Vcodecs) {
            comboBoxVideo.addItem(codec);
        }

        ///////////////////
        //////////////
        VLCTranscode.Acodec[] Acodecs = VLCTranscode.Acodec.values();
        for (VLCTranscode.Acodec codec: Acodecs) {
            comboBoxAudio.addItem(codec);
        }
        ///////////////////

        this.transcoder = new VLCTranscode("C:/Program Files (x86)/VideoLAN/VLC/vlc.exe");
        fileButton.addActionListener(a -> {
            toTranscode =  Transcode.MainKt.getFile();
            JLabelFile.setText(toTranscode);
        });
        buttonTranscode.addActionListener(a ->{
            textArea1.setText("");

            String list = transcoder.Transcode(
                    toTranscode,
                    (VLCTranscode.Vcodec) Objects.requireNonNull(comboBoxVideo.getSelectedItem()),
                    (VLCTranscode.Acodec) Objects.requireNonNull(comboBoxAudio.getSelectedItem()),
                    (Double.valueOf( spinnerScale.getValue().toString())),
                    (Integer) spinnerVideo.getValue(),
                    (Integer) spinnerAudio.getValue(),
                    (Integer) spinnerWidth.getValue(),
                    (Integer) spinnerHeight.getValue()
            );
            textArea1.setText(textArea1.getText()+"\n"+list);
        });
    }


}
