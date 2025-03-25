/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author Juan
 */
public class Time {
    public static float timeStarted = System.nanoTime();
    
    public static float getTime(){
        return (float) ((System.nanoTime() - timeStarted) * 1E-9); 
    }
    
    
    
}
