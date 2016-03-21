package ihy;

import com.jsyn.JSyn;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SineOscillator;
import com.softsynth.shared.time.TimeStamp;
import java.util.ArrayList;

public class SoundModule {

    private com.jsyn.Synthesizer synth;
    private LineOut lineOut;
    private Voice voice;
    private Voice voice1;
    private Voice voice2;
    private Voice voice3;
    private SineOscillator LFO;
    private TimeStamp timeStamp;
    private ArrayList<Character> pressedKeys;
    boolean trigger = true;
    private ArrayList<Voice> voices;
    
    private double freqMultiplier = 0.5;

    public SoundModule() {
        pressedKeys = new ArrayList<Character>(4);
        voices = new ArrayList<Voice>(4);
        synth = JSyn.createSynthesizer();        
        synth.start();
        synth.setRealTime(true);
        synth.add(lineOut = new LineOut());
        voice = new Voice();
        voice1 = new Voice();
        voice2 = new Voice();
        voice3 = new Voice();  
        LFO = new SineOscillator();
        
        synth.add(voice);
        synth.add(voice1);
        synth.add(voice2);
        synth.add(voice3);
        synth.add(LFO);
        timeStamp = new TimeStamp(2);        
        voice.getOutput().connect(0, lineOut.input, 0);
        voice.getOutput().connect(0, lineOut.input, 1);
        voice1.getOutput().connect(0, lineOut.input, 0);
        voice1.getOutput().connect(0, lineOut.input, 1);
        voice2.getOutput().connect(0, lineOut.input, 0);
        voice2.getOutput().connect(0, lineOut.input, 1);
        voice3.getOutput().connect(0, lineOut.input, 0);
        voice3.getOutput().connect(0, lineOut.input, 1);
        lineOut.start();
        
        voices.add(voice);
        voices.add(voice1);
        voices.add(voice2);
        voices.add(voice3);
    }

    public synchronized void playNote(double tone, char key) { 
        for (char temp: pressedKeys)
        {
            if (temp == key){
                trigger = false;
            }
        }
        if (trigger) {
            boolean noteIsPlayed = false;
            for (Voice temp: voices)
            {
                if (!temp.isBusy())
                {
                    if (!noteIsPlayed)
                    {
                        temp.noteOn(tone * freqMultiplier, 0.5, timeStamp, key);
                        noteIsPlayed = true;
                        pressedKeys.add(key);
                    }
                }
            }
        }
    }

    public synchronized void muteNote(char key) {
        pressedKeys.remove((Object)key);
        for (Voice temp: voices){
            if (temp.getPlayedKey() == key)
            {
                temp.noteOff(timeStamp);                
            }            
        }      
        trigger = true;
    }

    public void setFreqMultiplier(double freqMultiplier) {
        this.freqMultiplier = freqMultiplier;
    }
    
    //////КОНТРОЛЬ ОГИБАЮЩЕЙ ГРОМКОСТИ////////////    
    public void setAmpAttack(double value){
        voice.setAmpAttack(value);
        voice1.setAmpAttack(value);
        voice2.setAmpAttack(value);
        voice3.setAmpAttack(value);
    }
    public void setAmpDecay(double value){
        voice.setAmpDecay(value);
        voice1.setAmpDecay(value);
        voice2.setAmpDecay(value);
        voice3.setAmpDecay(value);
    }
    public void setAmpSustain(double value){
        voice.setAmpSustain(value);
        voice1.setAmpSustain(value);
        voice2.setAmpSustain(value);
        voice3.setAmpSustain(value);
    }
    public void setAmpRelease(double value){
        voice.setAmpRelease(value);
        voice1.setAmpRelease(value);
        voice2.setAmpRelease(value);
        voice3.setAmpRelease(value);
    }
    ///////////////////////////////////////////////
    
    //////КОНТРОЛЬ ОГИБАЮЩЕЙ ГРОМКОСТИ////////////    
    public void setFilterAttack(double value){
        voice.setFilterAttack(value);
        voice1.setFilterAttack(value);
        voice2.setFilterAttack(value);
        voice3.setFilterAttack(value);
    }
    public void setFilterDecay(double value){
        voice.setFilterDecay(value);
        voice1.setFilterDecay(value);
        voice2.setFilterDecay(value);
        voice3.setFilterDecay(value);
    }
    public void setFilterSustain(double value){
        voice.setFilterSustain(value);
        voice1.setFilterSustain(value);
        voice2.setFilterSustain(value);
        voice3.setFilterSustain(value);
    }
    public void setFilterRelease(double value){
        voice.setFilterRelease(value);
        voice1.setFilterRelease(value);
        voice2.setFilterRelease(value);
        voice1.setFilterRelease(value);
    }
    ///////////////////////////////////////////////
    
    ////ЧАСТОТА СРЕЗА ФИЛЬТРА И ДИАПАЗОН ЕЕ ИЗМЕНЕНИЯ//////
    public void setCutoff(double value){
        voice.setCutoff(value);
        voice1.setCutoff(value);
        voice2.setCutoff(value);
        voice3.setCutoff(value);
    }
    public void setCutoffRange(double value){
        voice.setCutoffRange(value); 
        voice1.setCutoffRange(value); 
        voice2.setCutoffRange(value); 
        voice3.setCutoffRange(value); 
    }
    public void setResonance(double value){
        voice.setResonance(value);
        voice1.setResonance(value);
        voice2.setResonance(value);
        voice3.setResonance(value);
    }
    ////////////////////////////////////////////////////////
    
    
    ///////////ГРОМКОСТИ ОСЦИЛЛЯТОРОВ///////////
    public void setSawAmplitude(double value){
        voice.setSawAmplitude(value);
        voice1.setSawAmplitude(value);
        voice2.setSawAmplitude(value);
        voice3.setSawAmplitude(value);
    }
    public void setSquareAmplitude(double value){
        voice.setSquareAmplitude(value);
        voice1.setSquareAmplitude(value);
        voice2.setSquareAmplitude(value);
        voice3.setSquareAmplitude(value);
    }
    public void setTriangleAmplitude(double value){
        voice.setTriangleAmplitude(value);
        voice1.setTriangleAmplitude(value);
        voice2.setTriangleAmplitude(value);
        voice3.setTriangleAmplitude(value);        
    }
    public void setNoiseAmplitude(double value){
        voice.setNoiseAmplitude(value);
        voice1.setNoiseAmplitude(value);
        voice2.setNoiseAmplitude(value);
        voice3.setNoiseAmplitude(value);
    }    
    /////////////////////////////////////////////
}
