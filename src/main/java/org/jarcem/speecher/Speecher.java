package org.jarcem.speecher;
/*
  User: Jarcem
  Date: 2019/3/18
  Purpose: text to speech
*/

import marytts.LocalMaryInterface;
import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;

import javax.sound.sampled.*;

public class Speecher {
    public static void toMary(String str) throws Exception{
        LocalMaryInterface mary = null;
        try {
            mary = new LocalMaryInterface();
        } catch (MaryConfigurationException e) {
            System.err.println("Could not initialize MaryTTS interface: " + e.getMessage());
            throw e;
        }

        // synthesize
        AudioInputStream audio = null;
        try {
            System.out.println("started");
            audio = mary.generateAudio(str);
            speech(audio);
        } catch (SynthesisException e) {
            System.err.println("Synthesis failed: " + e.getMessage());
            System.exit(1);
        }
    }
    public static void speech(AudioInputStream audio){
        try {
            //play audio
            AudioFormat audioFormat = audio.getFormat();
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat, AudioSystem.NOT_SPECIFIED);
            SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            sourceDataLine.open(audioFormat);
            sourceDataLine.start();

            //read data to mixer from stream
            int count;
            byte tempBuffer[] = new byte[1024];
            while ((count = audio.read(tempBuffer, 0, tempBuffer.length)) != -1) {
                if (count > 0) {
                    sourceDataLine.write(tempBuffer, 0, count);
                }
            }
            // 清空数据缓冲,并关闭输入
            sourceDataLine.drain();
            sourceDataLine.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}