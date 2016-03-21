package ihy;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.Add;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.EnvelopeDAHDSR;
import com.jsyn.unitgen.FilterLowPass;
import com.jsyn.unitgen.Multiply;
import com.jsyn.unitgen.SawtoothOscillator;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.SquareOscillator;
import com.jsyn.unitgen.TriangleOscillator;
import com.jsyn.unitgen.UnitOscillator;
import com.jsyn.unitgen.UnitVoice;
import com.jsyn.unitgen.WhiteNoise;
import com.softsynth.shared.time.TimeStamp;


public class Voice extends Circuit implements UnitVoice {
    private UnitOscillator saw;
    private UnitOscillator square;
    private UnitOscillator triangle;
    private UnitOscillator sine;
    private WhiteNoise noise;
    private FilterLowPass filter;
    private EnvelopeDAHDSR ampEnv;
    private EnvelopeDAHDSR filterEnv;
    private Add cutoffAdder;
    private Multiply frequencyScaler;
    private char playedKey = '\0';

    public UnitInputPort sawAmplitude;
    public UnitInputPort squareAmplitude;
    public UnitInputPort frequency;
    public UnitInputPort pitchModulation;
    public UnitInputPort cutoff;
    public UnitInputPort cutoffRange;
    public UnitInputPort Q; 
    
    public Voice() {
        add(frequencyScaler = new Multiply());
        add(saw = new SawtoothOscillator());
        add(square = new SquareOscillator());
        add(noise = new WhiteNoise());
        add(triangle = new TriangleOscillator());
        add(ampEnv = new EnvelopeDAHDSR());
        add(filterEnv = new EnvelopeDAHDSR());
        add(filter = new FilterLowPass());
        add(cutoffAdder = new Add());
        add(sine = new SineOscillator());
        
        sine.frequency.set(10);
        sine.amplitude.set(1);
        //sine.output.connect(noise.amplitude);

        filterEnv.output.connect(cutoffAdder.inputA);
        cutoffAdder.output.connect(filter.frequency);
        frequencyScaler.output.connect(saw.frequency);
        frequencyScaler.output.connect(square.frequency);
        frequencyScaler.output.connect(triangle.frequency);
        saw.output.connect(filter.input);        
        square.output.connect(filter.input);
        triangle.output.connect(filter.input);
        noise.output.connect(filter.input);
        filter.output.connect(ampEnv.amplitude);      
        addPort(sawAmplitude = saw.amplitude, "Saw Amplitude");
        addPort(squareAmplitude = square.amplitude, "Square Amplitude");
        addPort(frequency = frequencyScaler.inputA, "Frequency");
        addPort(pitchModulation = frequencyScaler.inputB, "PitchMod");
        addPort(cutoff = cutoffAdder.inputB, "Cutoff");
        addPort(cutoffRange = filterEnv.amplitude, "CutoffRange");
        addPort(Q = filter.Q);

        ampEnv.export(this, "Amp");
        filterEnv.export(this, "Filter");

        frequency.setup(saw.frequency);
        frequency.setup(square.frequency);
        pitchModulation.setup(0.2, 1.0, 4.0);
        cutoff.setup(filter.frequency);
        cutoffRange.setup(filter.frequency);
        
        ampEnv.setupAutoDisable(this);

        
        usePreset(0);
    }

    @Override
    public void noteOff(TimeStamp timeStamp) {   
        playedKey = '\0';
        ampEnv.input.off(timeStamp);
        filterEnv.input.off(timeStamp);
    }

    @Override
    public void noteOn(double freq, double ampl, TimeStamp timeStamp) {        
        frequency.set(freq, timeStamp);       
        
        ampEnv.input.on(timeStamp);
        filterEnv.input.on(timeStamp);
    }
    
    public void noteOn(double freq, double ampl, TimeStamp timeStamp, char key) {
        playedKey = key;        
        frequency.set(freq, timeStamp);
        
        ampEnv.input.on(timeStamp);
        filterEnv.input.on(timeStamp);
    }

    @Override
    public UnitOutputPort getOutput() {
        return ampEnv.output;
    }

    @Override
    public void usePreset(int presetIndex) {
        ampEnv.attack.set(0.00);
        ampEnv.decay.set(0.8);
        ampEnv.sustain.set(0.2);
        ampEnv.release.set(1.0);
        filterEnv.attack.set(0.0);
        filterEnv.decay.set(0.7);
        filterEnv.sustain.set(0.0);
        filterEnv.release.set(0.3);
        cutoff.set(100.0);
        cutoffRange.set(15000.0);
        filter.Q.set(0.5);
        setSawAmplitude(0.50);
        setSquareAmplitude(0.0);
        setTriangleAmplitude(0.0);
        setNoiseAmplitude(0.15);
    }
    
    //////КОНТРОЛЬ ОГИБАЮЩЕЙ ГРОМКОСТИ////////////
    public void setAmpAttack(double value){
        ampEnv.attack.set(value);
    }
    public void setAmpDecay(double value){
        ampEnv.decay.set(value);
    }
    public void setAmpSustain(double value){
        ampEnv.sustain.set(value);
    }
    public void setAmpRelease(double value){
        ampEnv.release.set(value);
    }
    ///////////////////////////////////////////////
    
    //////КОНТРОЛЬ ОГИБАЮЩЕЙ ФИЛЬТРА////////////
    public void setFilterAttack(double value){
        filterEnv.attack.set(value);
    }
    public void setFilterDecay(double value){
        filterEnv.decay.set(value);
    }
    public void setFilterSustain(double value){
        filterEnv.sustain.set(value);
    }
    public void setFilterRelease(double value){
        filterEnv.release.set(value);
    }
    ///////////////////////////////////////////////
    
    ////////ЧАСТОТА СРЕЗА ФИЛЬТРА, ДИАПАЗОН ЕЕ ИЗМЕНЕНИЯ И РЕЗОНАНС
    public void setCutoff(double value){
        cutoff.set(value); 
    }
    public void setCutoffRange(double value){
        cutoffRange.set(value);
    }
    public void setResonance(double value){
        filter.Q.set(value);
    }
    /////////////////////////////
    
    public boolean isBusy() {
        if (playedKey == '\0') return false;
        else return true;
    }

    public char getPlayedKey() {
        return playedKey;
    }
    
    ///////////ГРОМКОСТИ ОСЦИЛЛЯТОРОВ//////////
    public void setSawAmplitude(double value){
        saw.amplitude.set(value);
    }
    public void setSquareAmplitude(double value){
        square.amplitude.set(value);
    }
    public void setTriangleAmplitude(double value){
        triangle.amplitude.set(value);
    }
    public void setNoiseAmplitude(double value){
        noise.amplitude.set(value);
    }
    ///////////////////////////////////////////
}