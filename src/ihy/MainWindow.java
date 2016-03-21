package ihy;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainWindow extends JFrame {

    private boolean chordMode = false;
    private boolean trigger = true;
    private ArrayList<Character> pressedKeys;
    private SoundModule soundModule;
    private JFrame window;
    private JPanel whiteKeys;
    private JPanel blackKeys;
    private JPanel whiteKeys2;
    private JPanel blackKeys2;
    private JPanel whiteKeys3;
    private JPanel blackKeys3;
    private JPanel octaveContainer1;
    private JPanel octaveContainer2;
    private JPanel octaveContainer3;

    private Font font;

    private JButton openPreset;
    private JButton savePreset;
    private JFileChooser fc = new JFileChooser();

    private JSlider octaveNumber;

    //ОГИБАЮЩИЕ ФИЛЬТРА И ЧАСТОТЫ СРЕЗА
    private JPanel leftPanel;
    private JSlider ampAttack;
    private JSlider ampDecay;
    private JSlider ampSustain;
    private JSlider ampRelease;
    private JSlider filterAttack;
    private JSlider filterDecay;
    private JSlider filterSustain;
    private JSlider filterRelease;
    //ЧАСТОТА СРЕЗА ФИЛЬТРА И ДИАПАЗОН ЕЕ ИЗМЕНЕНИЯ
    private JPanel centerPanel;
    private JSlider cutoff;
    private JSlider cutoffRange;
    private JSlider resonance;
    //ГРОМКОСТИ ОСЦИЛЛЯТОРОВ
    private JSlider sawVol;
    private JSlider squareVol;
    private JSlider triangleVol;
    private JSlider noiseVol;
    //ПРАВАЯ ПАНЕЛЬ
    private JPanel rightPanel;
    private JCheckBox chords;

    //первая октава
    private JButton C;
    private JButton D;
    private JButton E;
    private JButton F;
    private JButton G;
    private JButton A;
    private JButton H;
    private JButton Cc;
    private JButton Dd;
    private JButton Ff;
    private JButton Gg;
    private JButton Aa;
    //вторая октава
    private JButton C2;
    private JButton D2;
    private JButton E2;
    private JButton F2;
    private JButton G2;
    private JButton A2;
    private JButton H2;
    private JButton Cc2;
    private JButton Dd2;
    private JButton Ff2;
    private JButton Gg2;
    private JButton Aa2;
    //третья октава
    private JButton C3;
    private JButton D3;
    private JButton E3;
    private JButton F3;
    private JButton G3;
    private JButton A3;
    private JButton H3;
    private JButton Cc3;
    private JButton Dd3;
    private JButton Ff3;
    private JButton Gg3;
    private JButton Aa3;
    
    //надписи
    private JLabel AMPENVHEADERLBL;
    private JLabel AMPATTACKLBL;
    private JLabel AMPDECAYLBL;
    private JLabel AMPSUSTAINLBL;
    private JLabel AMPRELEASELBL;
    
    private JLabel FILTENVHEADERLBL;
    private JLabel FILTATTACKLBL;
    private JLabel FILTDECAYLBL;
    private JLabel FILTSUSTAINLBL;
    private JLabel FILTRELEASELBL;
    
    private JLabel FILTPARAMHEADERLBL;
    private JLabel FILTCUTOFFLBL;
    private JLabel FILTCUTOFFRANGELBL;
    private JLabel FILTQLBL;
    
    private JLabel OSCVOLHEADERLBL;
    private JLabel SAWVOLLBL;
    private JLabel SQUAREVOLLBL;
    private JLabel TRIANGLEVOLLBL;
    private JLabel NOISEVOLLBL;
    
    private JLabel OCTAVELBL;
    private JLabel LANGLBL;
    
    private JComboBox language;
    
    private ArrayList<JLabel> LBLLIST;
    boolean appLoaded = false;
    
    
    
    

    public MainWindow() {
        //ШРИФТ ДЛЯ JLabelов
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResource("segoeui.ttf").openStream());
        } catch (FontFormatException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        genv.registerFont(font);
        font = font.deriveFont(13f);
        
        

        soundModule = new SoundModule();
        window = new JFrame("Ihy");
        window.setLayout(new GridLayout(2, 3));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(700, 410);
        window.setLocation(100, 50);
        window.setResizable(false);
        window.setFocusable(true);
        whiteKeys = new JPanel();
        blackKeys = new JPanel();
        whiteKeys2 = new JPanel();
        blackKeys2 = new JPanel();
        whiteKeys3 = new JPanel();
        blackKeys3 = new JPanel();
        whiteKeys.setLayout(new GridLayout(1, 7));
        blackKeys.setLayout(new GridLayout(1, 13));
        whiteKeys2.setLayout(new GridLayout(1, 7));
        blackKeys2.setLayout(new GridLayout(1, 13));
        whiteKeys3.setLayout(new GridLayout(1, 7));
        blackKeys3.setLayout(new GridLayout(1, 13));
        octaveContainer1 = new JPanel();
        octaveContainer1.setLayout(new GridLayout(2, 1));
        octaveContainer2 = new JPanel();
        octaveContainer2.setLayout(new GridLayout(2, 1));
        octaveContainer3 = new JPanel();
        octaveContainer3.setLayout(new GridLayout(2, 1));

        //первая октава черные клавиши
        Cc = new JButton("");
        Cc.setEnabled(false);
        Dd = new JButton("");
        Dd.setEnabled(false);
        Ff = new JButton("");
        Ff.setEnabled(false);
        Gg = new JButton("");
        Gg.setEnabled(false);
        Aa = new JButton("");
        Aa.setEnabled(false);
        blackKeys.add(new JLabel(""));
        blackKeys.add(Cc);
        blackKeys.add(new JLabel(""));
        blackKeys.add(Dd);
        blackKeys.add(new JLabel(""));
        blackKeys.add(new JLabel(""));
        blackKeys.add(new JLabel(""));
        blackKeys.add(Ff);
        blackKeys.add(new JLabel(""));
        blackKeys.add(Gg);
        blackKeys.add(new JLabel(""));
        blackKeys.add(Aa);
        blackKeys.add(new JLabel(""));
        //первая октава белые клавиши
        C = new JButton("");
        C.setEnabled(false);
        D = new JButton("");
        D.setEnabled(false);
        E = new JButton("");
        E.setEnabled(false);
        F = new JButton("");
        F.setEnabled(false);
        G = new JButton("");
        G.setEnabled(false);
        A = new JButton("");
        A.setEnabled(false);
        H = new JButton("");
        H.setEnabled(false);
        whiteKeys.add(C);
        whiteKeys.add(D);
        whiteKeys.add(E);
        whiteKeys.add(F);
        whiteKeys.add(G);
        whiteKeys.add(A);
        whiteKeys.add(H);

        //вторая октава черные клавиши
        Cc2 = new JButton("");
        Cc2.setEnabled(false);
        Dd2 = new JButton("");
        Dd2.setEnabled(false);
        Ff2 = new JButton("");
        Ff2.setEnabled(false);
        Gg2 = new JButton("");
        Gg2.setEnabled(false);
        Aa2 = new JButton("");
        Aa2.setEnabled(false);
        blackKeys2.add(new JLabel(""));
        blackKeys2.add(Cc2);
        blackKeys2.add(new JLabel(""));
        blackKeys2.add(Dd2);
        blackKeys2.add(new JLabel(""));
        blackKeys2.add(new JLabel(""));
        blackKeys2.add(new JLabel(""));
        blackKeys2.add(Ff2);
        blackKeys2.add(new JLabel(""));
        blackKeys2.add(Gg2);
        blackKeys2.add(new JLabel(""));
        blackKeys2.add(Aa2);
        blackKeys2.add(new JLabel(""));
        //вторая октава белые клавиши
        C2 = new JButton("");
        C2.setEnabled(false);
        D2 = new JButton("");
        D2.setEnabled(false);
        E2 = new JButton("");
        E2.setEnabled(false);
        F2 = new JButton("");
        F2.setEnabled(false);
        G2 = new JButton("");
        G2.setEnabled(false);
        A2 = new JButton("");
        A2.setEnabled(false);
        H2 = new JButton("");
        H2.setEnabled(false);
        whiteKeys2.add(C2);
        whiteKeys2.add(D2);
        whiteKeys2.add(E2);
        whiteKeys2.add(F2);
        whiteKeys2.add(G2);
        whiteKeys2.add(A2);
        whiteKeys2.add(H2);

        //треться октава черные клавиши
        Cc3 = new JButton("");
        Cc3.setEnabled(false);
        Dd3 = new JButton("");
        Dd3.setEnabled(false);
        Ff3 = new JButton("");
        Ff3.setEnabled(false);
        Gg3 = new JButton("");
        Gg3.setEnabled(false);
        Aa3 = new JButton("");
        Aa3.setEnabled(false);
        blackKeys3.add(new JLabel(""));
        blackKeys3.add(Cc3);
        blackKeys3.add(new JLabel(""));
        blackKeys3.add(Dd3);
        blackKeys3.add(new JLabel(""));
        blackKeys3.add(new JLabel(""));
        blackKeys3.add(new JLabel(""));
        blackKeys3.add(Ff3);
        blackKeys3.add(new JLabel(""));
        blackKeys3.add(Gg3);
        blackKeys3.add(new JLabel(""));
        blackKeys3.add(Aa3);
        blackKeys3.add(new JLabel(""));

        //треться октава белые клавиши
        C3 = new JButton("");
        C3.setEnabled(false);
        D3 = new JButton("");
        D3.setEnabled(false);
        E3 = new JButton("");
        E3.setEnabled(false);
        F3 = new JButton("");
        F3.setEnabled(false);
        G3 = new JButton("");
        G3.setEnabled(false);
        A3 = new JButton("");
        A3.setEnabled(false);
        H3 = new JButton("");
        H3.setEnabled(false);
        whiteKeys3.add(C3);
        whiteKeys3.add(D3);
        whiteKeys3.add(E3);
        whiteKeys3.add(F3);
        whiteKeys3.add(G3);
        whiteKeys3.add(A3);
        whiteKeys3.add(H3);

        octaveContainer1.add(blackKeys);
        octaveContainer1.add(whiteKeys);
        octaveContainer2.add(blackKeys2);
        octaveContainer2.add(whiteKeys2);
        octaveContainer3.add(blackKeys3);
        octaveContainer3.add(whiteKeys3);

        window.add(octaveContainer1);
        window.add(octaveContainer2);
        window.add(octaveContainer3);

        //КОНТРОЛЬ ОГИБАЮЩЕЙ ГРОМКОСТИ        
        ampAttack = new JSlider(0, 1, 100, 1);
        ampAttack.setMajorTickSpacing(10);
        ampAttack.setMinorTickSpacing(5);
        ampAttack.addChangeListener((e) -> {
            double value = (double) ampAttack.getValue() / 100;
            soundModule.setAmpAttack(value);
            window.requestFocus();
        });
        ampDecay = new JSlider(0, 1, 100, 80);
        ampDecay.setMajorTickSpacing(10);
        ampDecay.setMinorTickSpacing(5);
        ampDecay.addChangeListener((e) -> {
            double value = (double) ampDecay.getValue() / 100;
            soundModule.setAmpDecay(value);
            window.requestFocus();
        });
        ampSustain = new JSlider(0, 1, 100, 20);
        ampSustain.setMajorTickSpacing(10);
        ampSustain.setMinorTickSpacing(5);
        ampSustain.addChangeListener((e) -> {
            double value = (double) ampSustain.getValue() / 100;
            soundModule.setAmpSustain(value);
            window.requestFocus();
        });
        ampRelease = new JSlider(0, 1, 300, 100);
        ampRelease.setMajorTickSpacing(10);
        ampRelease.setMinorTickSpacing(5);
        ampRelease.addChangeListener((e) -> {
            double value = (double) ampRelease.getValue() / 100;
            soundModule.setAmpRelease(value);
            window.requestFocus();
        });
        /////////////////////////////////////////////

        //КОНТРОЛЬ ОГИБАЮЩЕЙ ФИЛЬТРА
        filterAttack = new JSlider(0, 1, 100, 1);
        filterAttack.setMajorTickSpacing(10);
        filterAttack.setMinorTickSpacing(5);
        filterAttack.addChangeListener((e) -> {
            double value = (double) filterAttack.getValue() / 100;
            soundModule.setFilterAttack(value);
            window.requestFocus();
        });
        filterDecay = new JSlider(0, 1, 100, 70);
        filterDecay.setMajorTickSpacing(10);
        filterDecay.setMinorTickSpacing(5);
        filterDecay.addChangeListener((e) -> {
            double value = (double) filterDecay.getValue() / 100;
            soundModule.setFilterDecay(value);
            window.requestFocus();
        });
        filterSustain = new JSlider(0, 1, 100, 1);
        filterSustain.setMajorTickSpacing(10);
        filterSustain.setMinorTickSpacing(5);
        filterSustain.addChangeListener((e) -> {
            double value = (double) filterSustain.getValue() / 100;
            soundModule.setFilterSustain(value);
            window.requestFocus();
        });
        filterRelease = new JSlider(0, 1, 100, 30);
        filterRelease.setMajorTickSpacing(10);
        filterRelease.setMinorTickSpacing(5);
        filterRelease.addChangeListener((e) -> {
            double value = (double) filterRelease.getValue() / 100;
            soundModule.setFilterRelease(value);
            window.requestFocus();
        });    
        //////////////////////////////////

        //ЧАСТОТА СРЕЗА ФИЛЬТРА И ДИАПАЗОН ЕЕ ИЗМЕНЕНИЯ        
        cutoff = new JSlider(0, 1, 20000, 100);
        cutoff.setMajorTickSpacing(1000);
        cutoff.setMinorTickSpacing(500);
        cutoff.addChangeListener((e) -> {
            double value = (double) cutoff.getValue();
            soundModule.setCutoff(value);
            window.requestFocus();
        });
        cutoffRange = new JSlider(0, 1, 20000, 15000);
        cutoffRange.setMajorTickSpacing(1000);
        cutoffRange.setMinorTickSpacing(500);
        cutoffRange.addChangeListener((e) -> {
            double value = (double) cutoffRange.getValue();
            soundModule.setCutoffRange(value);
            window.requestFocus();
        });
        resonance = new JSlider(0, 0, 20, 1);
        resonance.setMajorTickSpacing(1);
        resonance.addChangeListener((e) -> {
            double value = (double) resonance.getValue();
            if (value == 0) {
                soundModule.setResonance(0.5);
            } else {
                soundModule.setResonance(value);
            }
            window.requestFocus();
        });      
        /////////////////////////////////////////////////////////

        ////////////ГРОМКОСТИ ОСЦИЛЛЯТОРОВ//////////////////////
        sawVol = new JSlider(0, 0, 100, 50);
        sawVol.addChangeListener((e) -> {
            soundModule.setSawAmplitude((double) sawVol.getValue() / 100);
            window.requestFocus();
        });
        squareVol = new JSlider(0, 0, 100, 0);
        squareVol.addChangeListener((e) -> {
            soundModule.setSquareAmplitude((double) squareVol.getValue() / 100);
            window.requestFocus();
        });
        triangleVol = new JSlider(0, 0, 100, 0);
        triangleVol.addChangeListener((e) -> {
            soundModule.setTriangleAmplitude((double) triangleVol.getValue() / 100);
            window.requestFocus();
        });
        noiseVol = new JSlider(0, 0, 100, 15);
        noiseVol.addChangeListener((e) -> {
            soundModule.setNoiseAmplitude((double) noiseVol.getValue() / 100);
            window.requestFocus();
        });
        /////////////////////////////////////////////////////////////////////////

        octaveNumber = new JSlider(0, 0, 2, 0);
        octaveNumber.setPaintTicks(true);
        octaveNumber.setMajorTickSpacing(1);
        octaveNumber.addChangeListener((e) -> {
            if (octaveNumber.getValue() == 0) {
                soundModule.setFreqMultiplier(0.5);
            } else {
                soundModule.setFreqMultiplier((double) octaveNumber.getValue());
            }
            window.requestFocus();
        });
        
        chords = new JCheckBox("Chord Mode");
        chords.setFont(font);
        chords.addChangeListener((e) -> {
            if (chords.isSelected()) {
                chordMode = true;
            } else {
                chordMode = false;
            }
            window.requestFocus();
        });

        openPreset = new JButton("Open Preset");
        openPreset.setFont(font);
        openPreset.addActionListener((e) -> {
            FileFilter filter = new FileNameExtensionFilter("Ihy Preset (.ihy)", new String[]{"ihy"});
            fc.setFileFilter(filter);
            if (fc.showDialog(null, "Open Preset") == 0) {
                File preset = fc.getSelectedFile();
                try {
                    loadPreset(preset);
                } catch (IOException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            window.requestFocus();
        });
        
        savePreset = new JButton("Save Preset");
        savePreset.setFont(font);
        savePreset.addActionListener((e) -> {
            FileFilter filter = new FileNameExtensionFilter("Ihy Preset (.ihy)", new String[]{"ihy"});
            fc.setFileFilter(filter);
            if (fc.showSaveDialog(null) == 0) {
                try {
                    savePreset(fc.getSelectedFile());
                } catch (IOException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            window.requestFocus();
        });
        language = new JComboBox(new String[]{"English", "Русский"});
        language.setFont(font);
        language.addActionListener((e) -> {
            switch (language.getSelectedItem().toString()) {
                case "Русский": {
                    try {
                        loadLanguagePack(new File("russian.txt"));
                    } catch (IOException ex) {
                        Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                case "English":
                    try {
                        loadLanguagePack(new File("english.txt"));
                    } catch (IOException ex) {
                        Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
            }
            window.requestFocus();
        });
        //ЗАГРУЗКА ЯЗЫКА       
        try {
            loadLanguagePack(new File("english.txt"));
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        ///////////LEFT PANEL//////////////
        leftPanel = new JPanel(new GridLayout(10, 1));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 1, 2));
                
        leftPanel.add(AMPENVHEADERLBL);
        AMPENVHEADERLBL.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        JPanel leftPanelCell2 = new JPanel(new GridLayout(1, 2));
        leftPanelCell2.add(AMPATTACKLBL);        
        leftPanelCell2.add(AMPDECAYLBL);
        AMPATTACKLBL.setBorder(BorderFactory.createMatteBorder(0,1,0,0,Color.black));
        AMPDECAYLBL.setBorder(BorderFactory.createMatteBorder(0,0,0,1,Color.black));
        leftPanel.add(leftPanelCell2);
        JPanel leftPanelCell3 = new JPanel(new GridLayout(1, 2));
        leftPanelCell3.add(ampAttack);
        leftPanelCell3.add(ampDecay);        
        ampAttack.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,1,0,0,Color.black), BorderFactory.createEmptyBorder(0, 5, 0, 2)));
        ampDecay.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,0,0,1,Color.black), BorderFactory.createEmptyBorder(0, 3, 0, 5)));
        leftPanel.add(leftPanelCell3);
        JPanel leftPanelCell4 = new JPanel(new GridLayout(1, 2));
        leftPanelCell4.add(AMPSUSTAINLBL);
        leftPanelCell4.add(AMPRELEASELBL);
        AMPSUSTAINLBL.setBorder(BorderFactory.createMatteBorder(0,1,0,0,Color.black));
        AMPRELEASELBL.setBorder(BorderFactory.createMatteBorder(0,0,0,1,Color.black));
        leftPanel.add(leftPanelCell4);
        JPanel leftPanelCell5 = new JPanel(new GridLayout(1, 2));
        leftPanelCell5.add(ampSustain);
        leftPanelCell5.add(ampRelease);
        ampSustain.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,1,0,0,Color.black), BorderFactory.createEmptyBorder(0, 5, 0, 2)));
        ampRelease.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,0,0,1,Color.black), BorderFactory.createEmptyBorder(0, 3, 0, 5)));
        leftPanel.add(leftPanelCell5);
        leftPanel.add(FILTENVHEADERLBL);
        FILTENVHEADERLBL.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        JPanel leftPanelCell7 = new JPanel(new GridLayout(1, 2));
        leftPanelCell7.add(FILTATTACKLBL);
        leftPanelCell7.add(FILTDECAYLBL);
        FILTATTACKLBL.setBorder(BorderFactory.createMatteBorder(0,1,0,0,Color.black));
        FILTDECAYLBL.setBorder(BorderFactory.createMatteBorder(0,0,0,1,Color.black));
        leftPanel.add(leftPanelCell7);
        JPanel leftPanelCell8 = new JPanel(new GridLayout(1, 2));
        leftPanelCell8.add(filterAttack);
        leftPanelCell8.add(filterDecay);
        filterAttack.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,1,0,0,Color.black), BorderFactory.createEmptyBorder(0, 5, 0, 2)));
        filterDecay.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,0,0,1,Color.black), BorderFactory.createEmptyBorder(0, 3, 0, 5)));
        leftPanel.add(leftPanelCell8);
        JPanel leftPanelCell9 = new JPanel(new GridLayout(1, 2));
        leftPanelCell9.add(FILTSUSTAINLBL);
        leftPanelCell9.add(FILTRELEASELBL);
        FILTSUSTAINLBL.setBorder(BorderFactory.createMatteBorder(0,1,0,0,Color.black));
        FILTRELEASELBL.setBorder(BorderFactory.createMatteBorder(0,0,0,1,Color.black));
        leftPanel.add(leftPanelCell9);
        JPanel leftPanelCell10 = new JPanel(new GridLayout(1, 2));
        leftPanelCell10.add(filterSustain);
        leftPanelCell10.add(filterRelease);
        filterSustain.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,1,1,0,Color.black), BorderFactory.createEmptyBorder(0, 5, 0, 2)));
        filterRelease.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,0,1,1,Color.black), BorderFactory.createEmptyBorder(0, 3, 0, 5)));
        leftPanel.add(leftPanelCell10);
        window.add(leftPanel);
        ///////////LEFT PANEL//////////////
        
        ///////////CENTER PANEL//////////////
        centerPanel = new JPanel(new GridLayout(10, 1));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 1, 2));
                
        centerPanel.add(FILTPARAMHEADERLBL);
        FILTPARAMHEADERLBL.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        JPanel centerPanelCell2 = new JPanel(new GridLayout(1, 2));
        centerPanelCell2.add(FILTCUTOFFLBL);        
        centerPanelCell2.add(FILTCUTOFFRANGELBL);
        FILTCUTOFFLBL.setBorder(BorderFactory.createMatteBorder(0,1,0,0,Color.black));
        FILTCUTOFFRANGELBL.setBorder(BorderFactory.createMatteBorder(0,0,0,1,Color.black));
        centerPanel.add(centerPanelCell2);
        JPanel centerPanelCell3 = new JPanel(new GridLayout(1, 2));
        centerPanelCell3.add(cutoff);
        centerPanelCell3.add(cutoffRange);        
        cutoff.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,1,0,0,Color.black), BorderFactory.createEmptyBorder(0, 5, 0, 2)));
        cutoffRange.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,0,0,1,Color.black), BorderFactory.createEmptyBorder(0, 3, 0, 5)));
        centerPanel.add(centerPanelCell3);
        JPanel centerPanelCell4 = new JPanel(new GridLayout(1, 2));
        centerPanelCell4.add(FILTQLBL);
        JLabel fill = new JLabel("");
        centerPanelCell4.add(fill);
        FILTQLBL.setBorder(BorderFactory.createMatteBorder(0,1,0,0,Color.black));
        fill.setBorder(BorderFactory.createMatteBorder(0,0,0,1,Color.black));
        centerPanel.add(centerPanelCell4);
        JPanel centerPanelCell5 = new JPanel(new GridLayout(1, 2));
        centerPanelCell5.add(resonance);
        JLabel fill2 = new JLabel("");
        centerPanelCell5.add(fill2);
        resonance.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,1,0,0,Color.black), BorderFactory.createEmptyBorder(0, 5, 0, 2)));
        fill2.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,0,0,1,Color.black), BorderFactory.createEmptyBorder(0, 3, 0, 5)));
        centerPanel.add(centerPanelCell5);
        centerPanel.add(OSCVOLHEADERLBL);
        OSCVOLHEADERLBL.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        JPanel centerPanelCell7 = new JPanel(new GridLayout(1, 2));
        centerPanelCell7.add(SAWVOLLBL);
        centerPanelCell7.add(SQUAREVOLLBL);
        SAWVOLLBL.setBorder(BorderFactory.createMatteBorder(0,1,0,0,Color.black));
        SQUAREVOLLBL.setBorder(BorderFactory.createMatteBorder(0,0,0,1,Color.black));
        centerPanel.add(centerPanelCell7);
        JPanel centerPanelCell8 = new JPanel(new GridLayout(1, 2));
        centerPanelCell8.add(sawVol);
        centerPanelCell8.add(squareVol);
        sawVol.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,1,0,0,Color.black), BorderFactory.createEmptyBorder(0, 5, 0, 2)));
        squareVol.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,0,0,1,Color.black), BorderFactory.createEmptyBorder(0, 3, 0, 5)));
        centerPanel.add(centerPanelCell8);
        JPanel centerPanelCell9 = new JPanel(new GridLayout(1, 2));
        centerPanelCell9.add(TRIANGLEVOLLBL);
        centerPanelCell9.add(NOISEVOLLBL);
        TRIANGLEVOLLBL.setBorder(BorderFactory.createMatteBorder(0,1,0,0,Color.black));
        NOISEVOLLBL.setBorder(BorderFactory.createMatteBorder(0,0,0,1,Color.black));
        centerPanel.add(centerPanelCell9);
        JPanel centerPanelCell10 = new JPanel(new GridLayout(1, 2));
        centerPanelCell10.add(triangleVol);
        centerPanelCell10.add(noiseVol);
        triangleVol.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,1,1,0,Color.black), BorderFactory.createEmptyBorder(0, 5, 0, 2)));
        noiseVol.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,0,1,1,Color.black), BorderFactory.createEmptyBorder(0, 3, 0, 5)));
        centerPanel.add(centerPanelCell10);
        window.add(centerPanel);
        ///////////CENTER PANEL//////////////
        
        ///////////RIGHT PANEL//////////////
        rightPanel = new JPanel();
        GridLayout test2 = new GridLayout(6, 2);
        test2.setHgap(5);
        rightPanel.setLayout(test2);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 5));
        
        rightPanel.add(new JLabel(""));
        rightPanel.add(OCTAVELBL);
        rightPanel.add(chords);
        rightPanel.add(octaveNumber);
        rightPanel.add(LANGLBL);            
        rightPanel.add(new JLabel(""));
        rightPanel.add(language);
        rightPanel.add(new JLabel(""));
        rightPanel.add(new JLabel(""));
        rightPanel.add(new JLabel(""));
        rightPanel.add(savePreset);
        rightPanel.add(openPreset);
        window.add(rightPanel);
        ///////////RIGHT PANEL//////////////
        
        window.setFont(font);
        pressedKeys = new ArrayList<Character>(4);
        window.addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public synchronized void keyReleased(KeyEvent e) {
                soundModule.muteNote(e.getKeyChar());
                pressedKeys.remove((Object) e.getKeyChar());
                trigger = true;
                switch (e.getKeyChar()) {
                    //верхняя октава
                    case 'й':
                        C2.setEnabled(false);
                        if (chordMode) {
                            soundModule.muteNote('у');
                            soundModule.muteNote('е');
                            E2.setEnabled(false);
                            G2.setEnabled(false);
                        }
                        break;
                    case '2':
                        Cc2.setEnabled(false);
                        break;
                    case 'ц':
                        D2.setEnabled(false);
                        if (chordMode) {
                            soundModule.muteNote('к');
                            soundModule.muteNote('н');
                            F2.setEnabled(false);
                            A2.setEnabled(false);
                        }
                        break;
                    case '3':
                        Dd2.setEnabled(false);
                        break;
                    case 'у':
                        E2.setEnabled(false);
                        if (chordMode) {
                            soundModule.muteNote('е');
                            soundModule.muteNote('г');
                            G2.setEnabled(false);
                            H2.setEnabled(false);
                        }
                        break;
                    case 'к':
                        F2.setEnabled(false);
                        if (chordMode) {
                            soundModule.muteNote('н');
                            soundModule.muteNote('й');
                            A2.setEnabled(false);
                            C2.setEnabled(false);
                        }
                        break;
                    case '5':
                        Ff2.setEnabled(false);
                        break;
                    case 'е':
                        G2.setEnabled(false);
                        if (chordMode) {
                            soundModule.muteNote('г');
                            soundModule.muteNote('ц');
                            H2.setEnabled(false);
                            D2.setEnabled(false);
                        }
                        break;
                    case '6':
                        Gg2.setEnabled(false);
                        break;
                    case 'н':
                        A2.setEnabled(false);
                        if (chordMode) {
                            soundModule.muteNote('ш');
                            soundModule.muteNote('у');
                            C3.setEnabled(false);
                            E2.setEnabled(false);
                        }
                        break;
                    case '7':
                        Aa2.setEnabled(false);
                        break;
                    case 'г':
                        H2.setEnabled(false);
                        break;
                    case 'ш':
                        C3.setEnabled(false);
                        break;
                    case '9':
                        Cc3.setEnabled(false);
                        break;
                    case 'щ':
                        D3.setEnabled(false);
                        break;
                    case '0':
                        Dd3.setEnabled(false);
                        break;
                    case 'з':
                        E3.setEnabled(false);
                        break;
                    case 'х':
                        F3.setEnabled(false);
                        break;
                    case '=':
                        Ff3.setEnabled(false);
                        break;
                    case 'ъ':
                        G3.setEnabled(false);
                        break;
                    //нижняя октава
                    case 'я':
                        C.setEnabled(false);
                        break;
                    case 'ы':
                        Cc.setEnabled(false);
                        break;
                    case 'ч':
                        D.setEnabled(false);
                        break;
                    case 'в':
                        Dd.setEnabled(false);
                        break;
                    case 'с':
                        E.setEnabled(false);
                        break;
                    case 'м':
                        F.setEnabled(false);
                        break;
                    case 'п':
                        Ff.setEnabled(false);
                        break;
                    case 'и':
                        G.setEnabled(false);
                        break;
                    case 'р':
                        Gg.setEnabled(false);
                        break;
                    case 'т':
                        A.setEnabled(false);
                        break;
                    case 'о':
                        Aa.setEnabled(false);
                        break;
                    case 'ь':
                        H.setEnabled(false);
                        break;
                    case 'б':
                        C2.setEnabled(false);
                        break;
                    case 'д':
                        Cc2.setEnabled(false);
                        break;
                    case 'ю':
                        D2.setEnabled(false);
                        break;
                    case 'ж':
                        Dd2.setEnabled(false);
                        break;
                    case '.':
                        E2.setEnabled(false);
                        break;
                }
            }

            @Override
            public synchronized void keyTyped(KeyEvent e) {
                for (char temp : pressedKeys) {
                    if (temp == e.getKeyChar()) {
                        trigger = false;
                    } else {
                        trigger = true;
                    }
                }
                if (trigger) {
                    switch (e.getKeyChar()) {
                        //верхняя октава
                        case 'й':
                            soundModule.playNote(261.63, e.getKeyChar());
                            C2.setEnabled(true);
                            if (chordMode) {
                                soundModule.playNote(329.63, 'у');
                                soundModule.playNote(392.00, 'е');
                                E2.setEnabled(true);
                                G2.setEnabled(true);
                            }
                            break;
                        case '2':
                            soundModule.playNote(277.18, e.getKeyChar());
                            Cc2.setEnabled(true);
                            break;
                        case 'ц':
                            soundModule.playNote(293.66, e.getKeyChar());
                            D2.setEnabled(true);
                            if (chordMode) {
                                soundModule.playNote(349.23, 'к');
                                soundModule.playNote(440.00, 'н');
                                F2.setEnabled(true);
                                A2.setEnabled(true);
                            }
                            break;
                        case '3':
                            soundModule.playNote(311.13, e.getKeyChar());
                            Dd2.setEnabled(true);
                            break;
                        case 'у':
                            soundModule.playNote(329.63, e.getKeyChar());
                            E2.setEnabled(true);
                            if (chordMode) {
                                soundModule.playNote(392.00, 'е');
                                soundModule.playNote(493.88, 'г');
                                G2.setEnabled(true);
                                H2.setEnabled(true);
                            }

                            break;
                        case 'к':
                            soundModule.playNote(349.23, e.getKeyChar());
                            F2.setEnabled(true);
                            if (chordMode) {
                                soundModule.playNote(440.00, 'н');
                                soundModule.playNote(261.63, 'й');
                                A2.setEnabled(true);
                                C2.setEnabled(true);
                            }
                            break;
                        case '5':
                            soundModule.playNote(369.99, e.getKeyChar());
                            Ff2.setEnabled(true);
                            break;
                        case 'е':
                            soundModule.playNote(392.00, e.getKeyChar());
                            G2.setEnabled(true);
                            if (chordMode) {
                                soundModule.playNote(493.88, 'г');
                                soundModule.playNote(293.66, 'ц');
                                H2.setEnabled(true);
                                D2.setEnabled(true);
                            }
                            break;
                        case '6':
                            soundModule.playNote(415.30, e.getKeyChar());
                            Gg2.setEnabled(true);
                            break;
                        case 'н':
                            soundModule.playNote(440.00, e.getKeyChar());
                            A2.setEnabled(true);
                            if (chordMode) {
                                soundModule.playNote(523.25, 'ш');
                                soundModule.playNote(329.63, 'у');
                                C3.setEnabled(true);
                                E2.setEnabled(true);
                            }
                            break;
                        case '7':
                            soundModule.playNote(466.16, e.getKeyChar());
                            Aa2.setEnabled(true);
                            break;
                        case 'г':
                            soundModule.playNote(493.88, e.getKeyChar());
                            H2.setEnabled(true);
                            break;
                        case 'ш':
                            soundModule.playNote(523.25, e.getKeyChar());
                            C3.setEnabled(true);
                            break;
                        case '9':
                            soundModule.playNote(554.37, e.getKeyChar());
                            Cc3.setEnabled(true);
                            break;
                        case 'щ':
                            soundModule.playNote(587.33, e.getKeyChar());
                            D3.setEnabled(true);
                            break;
                        case '0':
                            soundModule.playNote(622.25, e.getKeyChar());
                            Dd3.setEnabled(true);
                            break;
                        case 'з':
                            soundModule.playNote(659.25, e.getKeyChar());
                            E3.setEnabled(true);
                            break;
                        case 'х':
                            soundModule.playNote(698.46, e.getKeyChar());
                            F3.setEnabled(true);
                            break;
                        case '=':
                            soundModule.playNote(739.99, e.getKeyChar());
                            Ff3.setEnabled(true);
                            break;
                        case 'ъ':
                            soundModule.playNote(783.99, e.getKeyChar());
                            G3.setEnabled(true);
                            break;
                        //нижняя октава
                        case 'я':
                            soundModule.playNote(130.81, e.getKeyChar());
                            C.setEnabled(true);
                            break;
                        case 'ы':
                            soundModule.playNote(138.59, e.getKeyChar());
                            Cc.setEnabled(true);
                            break;
                        case 'ч':
                            soundModule.playNote(146.83, e.getKeyChar());
                            D.setEnabled(true);
                            break;
                        case 'в':
                            soundModule.playNote(155.56, e.getKeyChar());
                            Dd.setEnabled(true);
                            break;
                        case 'с':
                            soundModule.playNote(164.81, e.getKeyChar());
                            E.setEnabled(true);
                            break;
                        case 'м':
                            soundModule.playNote(174.61, e.getKeyChar());
                            F.setEnabled(true);
                            break;
                        case 'п':
                            soundModule.playNote(185.00, e.getKeyChar());
                            Ff.setEnabled(true);
                            break;
                        case 'и':
                            soundModule.playNote(196.00, e.getKeyChar());
                            G.setEnabled(true);
                            break;
                        case 'р':
                            soundModule.playNote(207.65, e.getKeyChar());
                            Gg.setEnabled(true);
                            break;
                        case 'т':
                            soundModule.playNote(220.00, e.getKeyChar());
                            A.setEnabled(true);
                            break;
                        case 'о':
                            soundModule.playNote(233.08, e.getKeyChar());
                            Aa.setEnabled(true);
                            break;
                        case 'ь':
                            soundModule.playNote(246.94, e.getKeyChar());
                            H.setEnabled(true);
                            break;
                        case 'б':
                            soundModule.playNote(261.63, e.getKeyChar());
                            C2.setEnabled(true);
                            break;
                        case 'д':
                            soundModule.playNote(277.18, e.getKeyChar());
                            Cc2.setEnabled(true);
                            break;
                        case 'ю':
                            soundModule.playNote(293.66, e.getKeyChar());
                            D2.setEnabled(true);
                            break;
                        case 'ж':
                            soundModule.playNote(311.13, e.getKeyChar());
                            Dd2.setEnabled(true);
                            break;
                        case '.':
                            soundModule.playNote(329.63, e.getKeyChar());
                            E2.setEnabled(true);
                            break;
                    }
                    pressedKeys.add(e.getKeyChar());
                }
            }
        });
        window.setVisible(true);
    }

    private void loadPreset(File preset) throws IOException {
        List<String> stringList = Files.readAllLines(preset.toPath(), Charset.defaultCharset());
        int i = 1;
        for (String str : stringList) {            
            str = str.split(" ")[1];
            switch (i) {
                case 1:
                    ampAttack.setValue(Integer.parseInt(str));
                    soundModule.setAmpAttack(Double.parseDouble(str) / 100);
                    break;
                case 2:
                    ampDecay.setValue(Integer.parseInt(str));
                    soundModule.setAmpDecay(Double.parseDouble(str) / 100);
                    break;
                case 3:
                    ampSustain.setValue(Integer.parseInt(str));
                    soundModule.setAmpSustain(Double.parseDouble(str) / 100);
                    break;
                case 4:
                    ampRelease.setValue(Integer.parseInt(str));
                    soundModule.setAmpRelease(Double.parseDouble(str) / 100);
                    break;
                case 5:
                    filterAttack.setValue(Integer.parseInt(str));
                    soundModule.setFilterAttack(Double.parseDouble(str) / 100);
                    break;
                case 6:
                    filterDecay.setValue(Integer.parseInt(str));
                    soundModule.setFilterDecay(Double.parseDouble(str) / 100);
                    break;
                case 7:
                    filterSustain.setValue(Integer.parseInt(str));
                    soundModule.setFilterSustain(Double.parseDouble(str) / 100);
                    break;
                case 8:
                    filterRelease.setValue(Integer.parseInt(str));
                    soundModule.setFilterRelease(Double.parseDouble(str) / 100);
                    break;
                case 9:
                    cutoff.setValue(Integer.parseInt(str));
                    soundModule.setCutoff(Double.parseDouble(str));
                    break;
                case 10:
                    cutoffRange.setValue(Integer.parseInt(str));
                    soundModule.setCutoffRange(Double.parseDouble(str));
                    break;
                case 11:
                    if (Double.parseDouble(str) == 0) {
                        soundModule.setResonance(0.5);
                        resonance.setValue(0);
                    } else {
                        resonance.setValue(Integer.parseInt(str));
                        soundModule.setResonance(Integer.parseInt(str));
                    }
                    break;
                case 12:
                    sawVol.setValue(Integer.parseInt(str));
                    soundModule.setSawAmplitude(Double.parseDouble(str) / 100);
                    break;
                case 13:
                    squareVol.setValue(Integer.parseInt(str));
                    soundModule.setSquareAmplitude(Double.parseDouble(str) / 100);
                    break;
                case 14:
                    triangleVol.setValue(Integer.parseInt(str));
                    soundModule.setTriangleAmplitude(Double.parseDouble(str) / 100);
                    break;
                case 15:
                    noiseVol.setValue(Integer.parseInt(str));
                    soundModule.setNoiseAmplitude(Double.parseDouble(str) / 100);
                    break;
                case 16:
                    octaveNumber.setValue(Integer.parseInt(str));
                    if (Integer.parseInt(str) == 0) {
                        soundModule.setFreqMultiplier(0.5);
                    } 
                    else {
                        soundModule.setFreqMultiplier(Double.parseDouble(str));
                    }                    
                    break;
            }
            i++;
        }
    }
    
    private void savePreset(File preset) throws IOException {
        String[] stringArray = new String[16];
        int i = 0;
        for (String str : stringArray) {
            i++;
            switch (i) {
                case 1:
                    stringArray[i-1] = "ampAttack " + Integer.toString(ampAttack.getValue());
                    break;
                case 2:
                    stringArray[i-1] = "ampDecay " + Integer.toString(ampDecay.getValue());
                    break;
                case 3:
                    stringArray[i-1] = "ampSustain " + Integer.toString(ampSustain.getValue());
                    break;
                case 4:
                    stringArray[i-1] = "ampRelease " + Integer.toString(ampRelease.getValue());
                    break;
                case 5:
                    stringArray[i-1] = "filterAttack " + Integer.toString(filterAttack.getValue());
                    break;
                case 6:
                    stringArray[i-1] = "filterDecay " + Integer.toString(filterDecay.getValue());
                    break;
                case 7:
                    stringArray[i-1] = "filterSustain " + Integer.toString(filterSustain.getValue());
                    break;
                case 8:
                    stringArray[i-1] = "filterRelease " + Integer.toString(filterRelease.getValue());
                    break;
                case 9:
                    stringArray[i-1] = "cutoff " + Integer.toString(cutoff.getValue());
                    break;
                case 10:
                    stringArray[i-1] = "cutoffRange " + Integer.toString(cutoffRange.getValue());
                    break;
                case 11:
                    stringArray[i-1] = "resonance " + Integer.toString(resonance.getValue());  
                    break;
                case 12:
                    stringArray[i-1] = "sawVol " + Integer.toString(sawVol.getValue());
                    break;
                case 13:
                    stringArray[i-1] = "squareVol " + Integer.toString(squareVol.getValue());
                    break;
                case 14:
                    stringArray[i-1] = "triangleVol " + Integer.toString(triangleVol.getValue());
                    break;
                case 15:
                    stringArray[i-1] = "noiseVol " + Integer.toString(noiseVol.getValue());
                    break;
                case 16:
                    stringArray[i-1] = "octaveNumber " + Integer.toString(octaveNumber.getValue());
                    break;
            }
        }
        FileWriter fw = new FileWriter(preset + ".ihy");
        for (String str : stringArray) {
            fw.write(str + "\n");
        }        
        fw.close();        
    }
    
    private void loadLanguagePack(File lang) throws IOException {        
        if (!appLoaded) {
            LBLLIST = new ArrayList<>();
            LBLLIST.add(AMPENVHEADERLBL = new JLabel());
            LBLLIST.add(AMPATTACKLBL = new JLabel());
            LBLLIST.add(AMPDECAYLBL = new JLabel());
            LBLLIST.add(AMPSUSTAINLBL = new JLabel());
            LBLLIST.add(AMPRELEASELBL = new JLabel());
            LBLLIST.add(FILTENVHEADERLBL = new JLabel());
            LBLLIST.add(FILTATTACKLBL = new JLabel());
            LBLLIST.add(FILTDECAYLBL = new JLabel());
            LBLLIST.add(FILTSUSTAINLBL = new JLabel());
            LBLLIST.add(FILTRELEASELBL = new JLabel());
            LBLLIST.add(FILTPARAMHEADERLBL = new JLabel());
            LBLLIST.add(FILTCUTOFFLBL = new JLabel());
            LBLLIST.add(FILTCUTOFFRANGELBL = new JLabel());
            LBLLIST.add(FILTQLBL = new JLabel());
            LBLLIST.add(OSCVOLHEADERLBL = new JLabel());
            LBLLIST.add(SAWVOLLBL = new JLabel());
            LBLLIST.add(SQUAREVOLLBL = new JLabel());
            LBLLIST.add(TRIANGLEVOLLBL = new JLabel());
            LBLLIST.add(NOISEVOLLBL = new JLabel());
            LBLLIST.add(OCTAVELBL = new JLabel());
            LBLLIST.add(LANGLBL = new JLabel());
            appLoaded = true;
        }
        List<String> stringList;
        try {
            stringList = Files.readAllLines(lang.toPath(), Charset.defaultCharset());
        } catch (IOException ex) {
            stringList = Files.readAllLines(lang.toPath(), Charset.forName("UTF-8"));
        }
        int i = 0;
        for (JLabel lbl : LBLLIST) {
            lbl.setText(stringList.get(i));
            lbl.setFont(font);
            lbl.setHorizontalAlignment(SwingConstants.CENTER);
            i++;
        }
        chords.setText(stringList.get(i));
        i++;
        openPreset.setText(stringList.get(i));
        i++;
        savePreset.setText(stringList.get(i));
    }
}