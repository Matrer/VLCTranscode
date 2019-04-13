package Transcode

import java.io.BufferedReader
import java.io.InputStreamReader

class VLCTranscode(val programDirectory:String) {

    enum class Vcodec(val codec:String){
        MPEG_1("mp1v"),
        MPEG_2("mp2v"),
        MPEG_4("mp4v"),
        Windows_Media_Video_v1 ("WMV1"),
        Windows_Media_Video_v2 ("WMV2"),
        MJPEG("MJPG"),
        H264("H264"),
        Theora("theo"),
        Cinepak("cvid"),
        Flash_Video("FLV1"),
        Creative_YUV("CYUV"),
        Huffman_YUV("HFYU"),
        Microsoft_Video_v1("MSVC"),
    }
    enum class Acodec(val codec: String)
    {
        MPEG_audio("mpga"),
        MPEG_Layer3_audio("mp3"),
        Dolby_Digital_A52("a52"),
    }

    fun Transcode(videoPath:String, vcodec: Vcodec = Vcodec.H264, acodec: Acodec = Acodec.MPEG_Layer3_audio, fps:Double=23.976000, vb:Int=600, ab:Int=128,width:Int,height:Int):String
    {
        if (videoPath.isEmpty())
        {
            return "Brak pliku"
        }
        val rt = Runtime.getRuntime()
       // val adit = "-I dummy"
        val runtime = """"$programDirectory"  $videoPath --sout=#transcode{fps=$fps,width=$width,height=$height,vcodec=${vcodec.codec},acodec=${acodec.codec},vb=$vb,ab=$ab}:standard{access=file,mux=ts,dst=$videoPath.ts vlc://quit"""
        println(runtime)

        val proc = rt.exec(runtime)
        val error = BufferedReader(InputStreamReader(proc.errorStream))
        val input = BufferedReader(InputStreamReader(proc.inputStream))
        var exe =""
        while(proc.isAlive)
        {
            if(input.ready())
            {
                exe += input.lineSequence().joinToString("\n")
                System.out.println(exe)
            }
            if(error.ready())
            {
                exe += error.lineSequence().joinToString("\n")
                System.err.println(exe)
            }
        }
        return exe
    }
    fun getMetaInfo(videoPath:String){
        if (videoPath.isEmpty())
        {
            return
        }
        val rt = Runtime.getRuntime()
        // val adit = "-I dummy"
        val runtime = """"$programDirectory"  $videoPath --codec""""
        println(runtime)

        val proc = rt.exec(runtime)
        val error = BufferedReader(InputStreamReader(proc.errorStream))
        val input = BufferedReader(InputStreamReader(proc.inputStream))
        var exe =""
        while(proc.isAlive)
        {
            if(input.ready())
            {
                exe += input.lineSequence().joinToString("\n")
                System.out.println(exe)
            }
            if(error.ready())
            {
                exe += error.lineSequence().joinToString("\n")
                System.err.println(exe)
            }
        }
    }
}