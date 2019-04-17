package com.dianxiaoer.dutillibrary.loading.mkloader.util;


import com.dianxiaoer.dutillibrary.loading.mkloader.exception.InvalidNumberOfPulseException;
import com.dianxiaoer.dutillibrary.loading.mkloader.type.ClassicSpinner;
import com.dianxiaoer.dutillibrary.loading.mkloader.type.FishSpinner;
import com.dianxiaoer.dutillibrary.loading.mkloader.type.LineSpinner;
import com.dianxiaoer.dutillibrary.loading.mkloader.type.LoaderView;
import com.dianxiaoer.dutillibrary.loading.mkloader.type.PhoneWave;
import com.dianxiaoer.dutillibrary.loading.mkloader.type.Pulse;
import com.dianxiaoer.dutillibrary.loading.mkloader.type.Radar;
import com.dianxiaoer.dutillibrary.loading.mkloader.type.Sharingan;
import com.dianxiaoer.dutillibrary.loading.mkloader.type.TwinFishesSpinner;
import com.dianxiaoer.dutillibrary.loading.mkloader.type.Whirlpool;
import com.dianxiaoer.dutillibrary.loading.mkloader.type.Worm;

/**
 * Created by Tuyen Nguyen on 2/13/17.
 */

public class LoaderGenerator {

  public static LoaderView generateLoaderView(int type) {
    switch (type) {
      case 0:
        return new ClassicSpinner();
      case 1:
        return new FishSpinner();
      case 2:
        return new LineSpinner();
      case 3:
        try {
          return new Pulse(3);
        } catch (InvalidNumberOfPulseException e) {
          e.printStackTrace();
        }
      case 4:
        try {
          return new Pulse(4);
        } catch (InvalidNumberOfPulseException e) {
          e.printStackTrace();
        }
      case 5:
        try {
          return new Pulse(5);
        } catch (InvalidNumberOfPulseException e) {
          e.printStackTrace();
        }
      case 6:
        return new Radar();
      case 7:
        return new TwinFishesSpinner();
      case 8:
        return new Worm();
      case 9:
        return new Whirlpool();
      case 10:
        return new PhoneWave();
      case 11:
        return new Sharingan();
      default:
        return new ClassicSpinner();
    }
  }

  public static LoaderView generateLoaderView(String type) {
    switch (type) {
      case "ClassicSpinner":
        return new ClassicSpinner();
      case "FishSpinner":
        return new FishSpinner();
      case "LineSpinner":
        return new LineSpinner();
      case "ThreePulse":
        try {
          return new Pulse(3);
        } catch (InvalidNumberOfPulseException e) {
          e.printStackTrace();
        }
      case "FourPulse":
        try {
          return new Pulse(4);
        } catch (InvalidNumberOfPulseException e) {
          e.printStackTrace();
        }
      case "FivePulse":
        try {
          return new Pulse(5);
        } catch (InvalidNumberOfPulseException e) {
          e.printStackTrace();
        }
      case "Radar":
        return new Radar();
      case "TwinFishesSpinner":
        return new TwinFishesSpinner();
      case "Worm":
        return new Worm();
      case "Whirlpool":
        return new Whirlpool();
      case "PhoneWave":
        return new PhoneWave();
      case "Sharingan":
        return new Sharingan();
      default:
        return new ClassicSpinner();
    }
  }
}
