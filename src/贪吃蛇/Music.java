package 贪吃蛇;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

//创建Music类继承Thread线程类，实现游戏和音乐两个线程同时进行
public class Music extends Thread {
//音乐启动、暂停变量
	private boolean stop=false;
//定义私有变量音乐文件名称变量
  private String fileName;
  //定义私有最终变量缓存区大小
  private final int EXTERNAL_BUFFER_SIZE = 524288;
  //重写构造方法并设置传入文件位置
  public Music(String wavFile) {
  //将传入音乐文件赋值给fileName
      this.fileName = wavFile;
  }
//控制音乐暂停
    public void stopPlaying() {
    	stop=true;
    }
  //定义音乐运行方法run
  public void run() {
  //创建File对象soundFile，并打开音乐文件
  File soundFile = new File(fileName); 
   //检查播放音乐的文件名是否存在
   if (!soundFile.exists()) {
   //如果不存在输出不存在音乐文件提示信息
      System.err.println("Wave file not found:" + fileName);
      return;
   }
   // 设置循环播放
   while (stop==false) { 
      // 创建音频输入流对象
      AudioInputStream audioInputStream = null; 
      // 调用try{}catch{}异常处理语句
      try {
      // 创建音频对象
        audioInputStream = AudioSystem.getAudioInputStream(soundFile);
      //音频文件打开异常
        } catch (UnsupportedAudioFileException e1) {
        //打印异常，并显示调用信息
        e1.printStackTrace();
        return;
        //输入/输出异常
        } catch (IOException e1) {
        //打印异常，并显示调用信息
        e1.printStackTrace();
        return;
        }
  // 设置音频格式
  AudioFormat format = audioInputStream.getFormat(); 
  // 定义源数据线
  SourceDataLine auline = null; 
  //受数据行支持的音频格式创建源数据行对象，保存源数据线信息
  DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
  // 调用try{}catch{}异常处理语句
  try {
       //获取源数据线
       auline = (SourceDataLine) AudioSystem.getLine(info);
       //打开音频格式
       auline.open(format);
       //数据行异常
       } catch (LineUnavailableException e) {
       //打印异常，并显示调用信息
       e.printStackTrace();
       return;
       //通常异常类异常
       } catch (Exception e) {
       //打印异常，并显示调用信息
       e.printStackTrace();
       return;
       }
 
   //源数据线开始工作
   auline.start();
   //定义音频文件所含全部字长存储变量
   int nBytesRead = 0;
   //定义缓冲区大小
   byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
   // 调用try{}catch{}异常处理语句
   try {
       //判断文件是否读取完毕
       while (nBytesRead != -1) {
       //获取音频文件所含全部字长并赋值
        nBytesRead = audioInputStream.read(abData, 0, abData.length);
        if (nBytesRead >= 0)
        //重写入音频
             auline.write(abData, 0, nBytesRead);
         }
         //输入/输出异常
         } catch (IOException e) {
         //打印异常，并显示调用信息
         e.printStackTrace();
         return;
        } finally {
        //将当前流中所有缓冲数据写入底层流，防止数据缺失
        auline.drain();
        //关闭源数据线
        auline.close();
        }
      }
    }
}
