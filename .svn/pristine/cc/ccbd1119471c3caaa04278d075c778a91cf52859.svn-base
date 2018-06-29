package com.libra.sinvoice;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.zhaoguan.mpluslibs.libra.crc;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by fanxu on 17/1/3.
 */

public class MyVoice implements SinVoiceRecognition.Listener ,SinVoicePlayer.Listener{
    private final static String tag = "MyVoiceRecognition";

    private boolean mIsReadFromFile;
    private final static int[] TOKENS = { 32, 32, 32, 32, 32, 32 };
    private final static String TOKENS_str = "Beeba20141";
    private final static int TOKEN_LEN = TOKENS.length;
    private SinVoiceRecognition mRecognition;
    private SinVoicePlayer mPlayer;
    private Context _con;
    private Handler mHanlder;
    private char mRecgs[] = new char[100];
    private int mRecgCount;
    private String mSdcardPath;
    private listener mListener;
    private ArrayList mArrayMsg;


    private final static int MSG_SET_RECG_TEXT = 1;
    private final static int MSG_RECG_START = 2;
    private final static int MSG_RECG_END = 3;
    private final static int MSG_PLAY_TEXT = 4;

    private final static String BAKCUP_LOG_PATH = "/sinvoice_backup";

    public final static int MSG_TYPE_LENGTH = 2;
    public final static int MSG_CONTENT_LENGTH = 32;

    private final static int MSG_SEND_MAX_CNT = 100;
    private int _msgCurCnt = 0;
    private int _msgCurListCnt = 0;


    static {
        Log.i(tag, "log library sinvoice crc");
    }

    public static class VoiceMessage{
        public char mType[] = new char[MSG_TYPE_LENGTH];
        public char mMsg[] = new char[MSG_CONTENT_LENGTH];
        public char mCRCPer;
        public char mCRCCur;
        public char mEnd;
    }

    @Override
    public void onSinVoiceRecognitionStart() {
        mHanlder.sendEmptyMessage(MSG_RECG_START);
    }

    @Override
    public void onSinVoiceRecognition(char ch) {
        mHanlder.sendMessage(mHanlder.obtainMessage(MSG_SET_RECG_TEXT, ch, 0));
    }

    @Override
    public void onSinVoiceRecognitionEnd(int result) {
        Log.e(tag, "onSinVoiceRecognitionEnd");
        mHanlder.sendMessage(mHanlder.obtainMessage(MSG_RECG_END, result, 0));
    }

    @Override
    public void onSinVoicePlayStart() {

    }

    @Override
    public void onSinVoicePlayEnd() {
        Log.e(tag, "onSinVoicePlayEnd");
        mHanlder.sendEmptyMessage(MSG_PLAY_TEXT);
    }

    @Override
    public void onSinToken(int[] tokens) {

    }

    public static interface listener {
        void onMsg(ArrayList msg);
    }

    public MyVoice(){
    }

    public void init(Context con, listener l){
        mIsReadFromFile = false;
        mRecgCount = 0;
        mSdcardPath = Environment.getExternalStorageDirectory().getPath();
        System.loadLibrary("CRCLibrary");
        System.loadLibrary("sinvoice");
        //clearBackup();
        mListener = l;
        mArrayMsg = new ArrayList();
        mRecognition = new SinVoiceRecognition();
        _con = con;
        mRecognition.init(_con);
        mRecognition.setListener(this);

        mPlayer = new SinVoicePlayer();
        mPlayer.init(_con);
        mPlayer.setListener(this);

        mHanlder = new RegHandler(this);
    }

    public void startRecognition(){
        mRecognition.start(TOKEN_LEN, mIsReadFromFile);
    }

    public void stopRecognition(){
        mRecognition.stop();
    }

    public static class VoiceMessageForClient{
        public String mType;
        public String mMsg;
        public char    mEnd;
        public char   mCrcPre;
        public char   mCrcCur;

        private VoiceMessageForClient(){

        }

        public VoiceMessageForClient(String type, String msg, char end){
            mType = type;
            mMsg = msg;
            mEnd = end;
            mCrcPre = '0';
            mCrcCur = '0';
        }
    }

    private ArrayList<VoiceMessageForClient> mClientMsgList = null;
    public void sendMsg(ArrayList<VoiceMessageForClient> msgs){
        if (msgs == null || msgs.size() <= 0){
            Log.e(tag, "msgs is empty");
            return;
        }

        if (mClientMsgList != null){
            Log.e(tag, "there are some msgs already in sending");
            return;
        }

        mClientMsgList = new ArrayList<>();
        mClientMsgList.addAll(msgs);
        _msgCurCnt = 0;
        _msgCurListCnt = 0;
        _sendTimeHandler.postDelayed(_sendTimeRun, 20);
    }

    public void stopSendMsg(){
        if (mClientMsgList != null){
            mClientMsgList.clear();
            mClientMsgList = null;
        }

        _sendTimeHandler.removeCallbacks(_sendTimeRun);

        mPlayer.stop();
    }

    private void sendMsg(){
        try {
            VoiceMessageForClient msg = mClientMsgList.get(_msgCurListCnt);
            int tokens[] = new int[37];
            for (int i = 0; i < tokens.length; i++) {
                tokens[i] = 0x1D;
            }

            byte[] type = msg.mType.getBytes("UTF8");
            byte[] context = msg.mMsg.getBytes("UTF8");
            int typeLength = MSG_TYPE_LENGTH > type.length ? type.length : MSG_TYPE_LENGTH;
            for (int i = 0; i < typeLength; i++){
                tokens[i] = type[i];
            }

            int conLength = MSG_CONTENT_LENGTH > context.length ? context.length : MSG_CONTENT_LENGTH;
            for (int i = 0; i < conLength; i++){
                tokens[i+2] = context[i];
            }

            char[] msgCon = new char[MSG_CONTENT_LENGTH];
            for (int i = 0; i < MSG_CONTENT_LENGTH; i++){
                msgCon[i] = (char) tokens[i+2];
            }

            if (msg.mCrcCur == '0') {
                msg.mCrcCur = crc.getCRC(msgCon, MSG_CONTENT_LENGTH);
            }
            tokens[34] = msg.mCrcCur;
            if (_msgCurListCnt > 0){
                if (msg.mCrcPre == '0'){
                    msg.mCrcPre = mClientMsgList.get(_msgCurListCnt-1).mCrcCur;
                }
                tokens[35] = msg.mCrcPre;
            }else
                tokens[35] = '0';

            tokens[36] = msg.mEnd;

            mPlayer.play(tokens, 37, false, 2000);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private Handler _sendTimeHandler = new Handler();
    private Runnable _sendTimeRun = new Runnable() {
        @Override
        public void run() {
            sendMsg();
        }
    };

    private static class RegHandler extends Handler {
        private MyVoice mAct;

        public RegHandler(MyVoice act) {
            mAct = act;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SET_RECG_TEXT:
                    char ch = (char) msg.arg1;
                    if (mAct.mRecgCount >= 99)
                        break;
                    mAct.mRecgs[mAct.mRecgCount++] = ch;
                    break;

                case MSG_RECG_START:
                    mAct.mRecgCount = 0;
                    break;

                case MSG_RECG_END:
                    LogHelper.e(tag, "recognition end gIsError:" + msg.arg1 + mAct.mRecgCount);
                    if ( mAct.mRecgCount >= 37 ) {
                        VoiceMessage vmsg = new VoiceMessage();
                        System.arraycopy(mAct.mRecgs,0,vmsg.mType,0,2);
                        System.arraycopy(mAct.mRecgs,2,vmsg.mMsg,0,32);
                        vmsg.mCRCCur = mAct.mRecgs[34];
                        vmsg.mCRCPer = mAct.mRecgs[35];
                        vmsg.mEnd = mAct.mRecgs[36];

                        Log.e(tag, String.format("rcv msg type %s, msg %s, cur %d, per %d, end %d",
                                String.valueOf(vmsg.mType), String.valueOf(vmsg.mMsg), (int)vmsg.mCRCCur, (int)vmsg.mCRCPer, (int)vmsg.mEnd));
                        char[] recg = new char[32];
                        System.arraycopy(mAct.mRecgs,2,recg,0,32);
                        char crcCur = crc.getCRC(recg, 32);
                        Log.e(tag, String.format("crcCur %d",(int)crcCur));
                        if (crcCur != vmsg.mCRCCur){
                            Log.e(tag,"msg trans error!!!");
                            break;
                        }

                        if (vmsg.mCRCPer == '0'){
                            mAct.mArrayMsg.clear();
                            mAct.mArrayMsg.add(vmsg);
                        } else {
                            if (mAct.mArrayMsg.size() >= 1) {
                                VoiceMessage msgPer = (VoiceMessage) mAct.mArrayMsg.get(mAct.mArrayMsg.size() - 1);
                                if (msgPer.mCRCCur == vmsg.mCRCPer) {
                                    mAct.mArrayMsg.add(vmsg);
                                } else {
                                    Log.e(tag, "msg not fit pre");
                                    break;
                                }
                            }
                        }

                        if (vmsg.mEnd == '1')
                            mAct.mListener.onMsg(mAct.mArrayMsg);


//                        byte[] strs = new byte[mAct.mRecgCount];
//                        for ( int i = 0; i < mAct.mRecgCount; ++i ) {
//                            strs[i] = (byte)mAct.mRecgs[i];
//                        }
//                        try {
//                            String strReg = new String(strs, "UTF8");
//                            if (msg.arg1 >= 0) {
//                                Log.e(tag, "reg ok!!!!!!!!!!!!");
//                                if (null != mAct) {
//                                    Log.e(tag, strReg);
//                                    // mAct.mRegState.setText("reg ok(" + msg.arg1 + ")");
//                                }
//                            } else {
//                                Log.e(tag, "reg error!!!!!!!!!!!!!");
//                                Log.e(tag, strReg);
//                                // mAct.mRegState.setText("reg err(" + msg.arg1 + ")");
//                                // mAct.mRegState.setText("reg err");
//                            }
//                        } catch (UnsupportedEncodingException e) {
//                            e.printStackTrace();
//                        }
                    }
                    break;

                case MSG_PLAY_TEXT:{

                    if(mAct.mClientMsgList == null)
                        break;

                    mAct._msgCurCnt++;
                    mAct._msgCurListCnt++;

                    if (mAct._msgCurListCnt >=mAct. mClientMsgList.size()){
                        mAct._msgCurListCnt = 0;
                    }

                    if (mAct._msgCurCnt >= MSG_SEND_MAX_CNT){
                        mAct._sendTimeHandler.removeCallbacks(mAct._sendTimeRun);
                        break;
                    }

                    mAct._sendTimeHandler.postDelayed(mAct._sendTimeRun, 20);
                }
                    break;
            }
            super.handleMessage(msg);
        }
    }



    private void clearBackup() {
        delete(new File(mSdcardPath + BAKCUP_LOG_PATH));

        //Toast.makeText(_con, "clear backup log info successful",
          //      Toast.LENGTH_SHORT).show();
    }

    private static void delete(File file) {
        if (!file.exists())
            return;

        if (file.isFile()) {
            file.delete();
            return;
        }

        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                return;
            }

            for (int i = 0; i < childFiles.length; i++) {
                delete(childFiles[i]);
            }
            file.delete();
        }
    }
}
