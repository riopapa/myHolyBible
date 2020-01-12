package com.urrecliner.myholybible;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class Vars {
    static File packageFolder = null;

    @SuppressLint("StaticFieldLeak")
    static Context mContext = null;
    @SuppressLint("StaticFieldLeak")
    static Activity mActivity = null;
    @SuppressLint("StaticFieldLeak")
    static MainActivity mainActivity = null;
    @SuppressLint("StaticFieldLeak")
    static SetActivity setActivity = null;
    @SuppressLint("StaticFieldLeak")
    static MakeBible makeBible = null;
    @SuppressLint("StaticFieldLeak")
    static MakeHymn makeHymn = null;
    static History history = null;
    static Utils utils = null;
    @SuppressLint("StaticFieldLeak")
    static ConstraintLayout constraintBody;
    static OnSwipeTouchListener onSwipeTouchListener;
    static float windowYUpper, windowXCenter;
    @SuppressLint("StaticFieldLeak")
    static ScrollView nowScrollView;

    static BookMarkAdapter bookMarkAdapter;
    static RecyclerView bookMarkView;

    static int nowBible = 0, nowChapter = 1, nowVerse = 0, nowHymn = 0, maxVerse = 0;
    static int xPixels, yPixels;
    static boolean blackMode;

    static String oldName = "구 약";
    static String newName = "신 약";
    static String hymnName = "찬송가";
    static int TAB_MODE_OLD = 1;
    static int TAB_MODE_NEW = 2;
    static int TAB_MODE_HYMN = 4;
    static int TAB_MODE_DIC = 8;

    static int topTab = TAB_MODE_NEW;    // if topTab < TAB_MODE_HYMN then it means OLD or NEW
    static String logFile = "log.txt";

    static boolean agpShow = false;
    static boolean cevShow = false;

    static int bibleColorFore, verseColorFore, paraColorFore, referColorFore, numberColorFore, cevColorFore, agpColorFore, dicColorFore, textColorBack, hymnColorFore, hymnColorTitle, hymnColorImage;

    @SuppressLint("StaticFieldLeak")
    static ViewGroup mBody; // assigned to Fragment_Body
    static ViewGroup mainScreen;
    static String nowDic;

    static int textSizeBibleNumber = 20;
    static int textSizeHymnTitle = 20;
    static int textSizeHymnKeypad = 24;

    // followings are from shared preference
    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;
    static int textSizeBible66 = 24;
    static int textSizeBibleTitle = 24;
    static int textSizeBibleRefer = 40;
    static int textSizeHymnBody = 20;
    static int textSizeBibleBody = 20;
    static int textSizeKeyWord = 20;

    static boolean hymnImageFirst = true;
    static int hymnShowWhat = 0;
    static boolean alwaysOn = true;
    static int textSizeSpace = 10;
    @SuppressLint("StaticFieldLeak")
    static TextView vCurrBible = null;
    static int normalMenuColor = 0;

    static final int SHEET_THEN_LYRIC = 0;
    static final int LYRIC_THEN_SHEET = 1;
    static final int SHEET_ONLY = 2;
    static final int LYRIC_ONLY = 3;

    static Text2Speech text2Speech = null;
    static boolean isReadingNow = false;
    static float bibleSpeed = 0.8f;
    static float biblePitch = 1.0f;
    static float hymnSpeed = 0.9f;

    static String [] bibleTexts;

    static ArrayList<GoBack> goBacks;
    static ArrayList<BookMark> bookMarks;

    static int [] nbrOfChapters = {0,50,40,27,36,34,24,21,4,31,24,22,25,29,36,10,13,10,42,150,31,12,8,66,52,5,48,12,14,3,9,1,4,7,3,3,3,2,14,4,28,16,24,21,28,16,16,13,6,6,4,4,5,3,6,4,3,1,13,5,5,3,5,1,1,1,22, 0 };

//    static String blank = "\u3040"; //전각 스페이스
    static String blank = "  "; //전각 스페이스
    static String [] fullBibleNames = {blank,"창세기","출애굽기","레위기","민수기","신명기","여호수아","사사기","룻기","사무엘상","사무엘하","열왕기상","열왕기하","역대상","역대하","에스라","느헤미야","에스더","욥기","시편","잠언","전도서","아가","이사야","예레미야","예레미야애가","에스겔","다니엘","호세아","요엘","아모스","오바댜","요나","미가","나훔","하박국","스바냐","학개","스가랴","말라기","마태복음","마가복음","누가복음","요한복음","사도행전","로마서","고린도전서","고린도후서","갈라디아서","에베소서","빌립보서","골로새서","데살로니가전서","데살로니가후서","디모데전서","디모데후서","디도서","빌레몬서","히브리서","야고보서","베드로전서","베드로후서","요한일서","요한이서","요한삼서","유다서","요한계시록",blank};

    static String [] shortBibleNames = {blank,"창","출","레","민","신","수","삿","룻","삼상","삼하","왕상","왕하","대상","대하","스","느","에","욥","시","잠","전","아","사","렘","애","겔","단","호","욜","암","옵","욘","미","나","합","습","학","슥","말","마","막","눅","요","행","롬","고전","고후","갈","엡","빌","골","살전","살후","딤전","딤후","딛","몬","히","약","벧전","벧후","요일","요이","요삼","유","계",blank};

    static String [] hymnTitles = {blank,"만복의 근원 하나님","찬양 성부 성자 성령","성부 성자와 성령","성부 성자와 성령","이 천지간 만물들아",	"목소리 높여서","성부 성자 성령","거룩 거룩 거룩 전능하신 주님","하늘에 가득찬 영광의 하나님","전능왕 오셔서","홀로 한분 하나님께","다 함께 주를 경배하세","영원한 하늘 나라","주 우리 하나님","하나님의 크신 사랑","은혜로신 하나님 우리주 하나님","사랑의 하나님","성도들아 찬양하자","찬송하는 소리 있어","큰 영광중에 계신 주","다 찬양 하여라","만유의 주 앞에","만입이 내게 있으면","왕되신 주","면류관 벗어서","구세주를 아는 이들","빛나고 높은 보좌와","복의 근원 강림하사","성도여 다 함께","전능하고 놀라우신",	"찬양하라 복되신 구세주 예수","만유의 주재","영광스런 주를 보라","참 놀랍도다 주 크신 이름","큰 영화로신 주",	"주 예수 이름 높이어","주 예수 이름 높이어","예수 우리 왕이여","주 은혜를 받으려","찬송으로 보답할 수 없는",	"내 영혼아 주 찬양하여라","거룩한 주님께","즐겁게 안식할 날","지난 이레 동안에","거룩한 주의 날","이 날은 주님 정하신","하늘이 푸르고","거룩하신 주 하나님","하나님이 언약하신 그대로","내게 있는 모든 것을",	"주님 주신 거룩한 날","거룩하신 나의 하나님","성전을 떠나 가기전","주여 복을 구하오니","주 이름으로 모였던","우리의 주여","오늘 주신 말씀에","지난밤에 보호하사","하나님 아버지 어둔 밤이 지나","영혼의 햇빛 예수님",	"우리가 기다리던","고요히 머리숙여","주가 세상을 다스리니","기뻐하며 경배하세","내 영혼아 찬양하라",	"다 감사드리세","영광의 왕께 다 경배하며","오 하나님 우리의 창조주시니","온 천하 만물 우러러","피난처 있으니",	"예부터 도움되시고","만왕의 왕앞에 나오라","내 눈을들어 두루 살피니","오 만세 반석이신","주여 우리 무리를",	"창조주 아버지께","거룩하신 하나님","저 높고 푸른 하늘과","주 하나님 지으신 모든 세계","천지에 있는 이름중",	"주는 귀한 보배","성부의 어린양이","나의 맘에 근심 구름","온 세상이 캄캄하여서","구주를 생각만 해도","내가 늘 의지하는 예수","내 주님 입으신 그 옷은","내 진정 사모하는","샤론의 꽃 예수","주 예수 내가 알기전",	"슬픈 마음 있는 사람","위에 계신 나의 친구","예수는 나의 힘이요","주 예수보다 더 귀한것은 없네","나의 기쁨 나의 소망되시며",	"예수님은 누구신가","정혼한 처녀에게","예수님 오소서","주님 앞에 떨며 서서","리암의 여인들이","이새의 뿌리에서","영원한 문아 열려라","우리 주님 예수께","곧 오소서 임마누엘","오랫동안 기다리던","아기예수 나셨네","거룩한 밤 복된 이 밤","그 어린 주 예수","고요한 밤 거룩한 밤","고요하고 거룩한 밤",	"귀중한 보배합을","그 맑고 환한 밤중에","저 아기 잠이 들었네","그 어린 주 예수","기쁘다 구주 오셨네","동방에서 박사들","만백성 기뻐하여라","영광 나라 천사들아","옛날 임금 다윗성에","오 베들레헴 작은골",	"우리 구주 나신날","참 반가운 성도여","저 들밖에 한 밤중에","양지키는 목자여","천사들의 노래가","천사 찬송하기를","그 고요하고 쓸쓸한","거룩하신 우리 주님","마리아는 아기를","찬란한 주의 영광은",	"다나와 찬송부르세","주의 영광 빛나니","하나님의 말씀으로","나 어느날 꿈속을 헤매며","어저께나 오늘이나","가나안의 혼인잔치","하나님의 아들이","햇빛을 받는 곳마다","오 영원한 내 주 예수","왕되신 우리 주께",	"호산나 호산나","시온에 오시는 주","웬 말인가 날 위하여","예수 나를 위하여","오 거룩하신 주님","저 멀리 푸른 언덕에","거기 너 있었는가","영화로운 주 예수의","주 달려 죽은 십자가","갈보리산 위에","만왕의 왕 내 주께서","귀하신 예수","가시 면류관","생명의 주여 면류관","십자가 지고","머리에 가시관 붉은 피 흐르는","겟세마네 동산에서 최후 기도","서쪽하늘 붉은 노을","기뻐 찬송하세","무덤에 머물러","할렐루야 우리 예수","부활하신 구세주","할렐루야 할렐루야","예수 부활 했으니","주님께 영광","싸움은 모두 끝나고","즐겁도다 이 날","하늘에 찬송이 들리던 그날","사망의 권세가","내 주님은 살아계셔","하나님의 독생자","사망을 이긴 주","다 함께 찬송부르자","대속하신 구주께서","신랑되신 예수께서","주 어느때 다시 오실는지","오랫동안 고대하던","주 예수 믿는자여","주 예수의 강림이","하나님의 나팔소리","부활 승천하신 주께서","강물같이 흐르는 기쁨","빈들에 마른 풀같이","불길 같은 주 성령","이기쁜 소식을","영화로신 주 성령","비둘기 같이 온유한","무한하신 주 성령","진실하신 주 성령","성령이여 강림하사","내가 매일 기쁘게","임하소서 임하소서","성령의 봄바람 불어오니","저 하늘 거룩하신 주여","성령이여 우리 찬송부를 때","성령의 은사를","은혜가 풍성한 하나님은","주 예수 해변서","나의 사랑하는 책","달고 오묘한 그 말씀","참 사람되신 말씀","하나님 아버지 주신 책은","하나님의 말씀은","주의 말씀 듣고서","주 예수 크신 사랑","주님의 귀한 말씀은","귀하신 주님 계신곳","내 주의 나라와","이 세상 풍파 심하고","시온성과 같은 교회","값비싼 향유를 주께 드린","겸손히 주를 섬길때","나의 생명 드리니","나 주의 도움 받고자","내 죄 속해주신 주께","성자의 귀한몸","하나님이 말씀하시기를","네 맘과 정성을 다하여서","주 하나님의 사랑은","사랑하는 주님앞에","주 믿는 형제들","우리 다시 만날때까지","하나님은 우리들의","정한 물로 우리 죄를","실로암 샘물가에 핀","성령으로 세례받아","주 앞에 성찬받기 위하여","오 나의 주님 친히 뵈오니","아무 흠도 없고","우리의 참되신 구주시니","우리 다같이 무릎 꿇고서","유월절 때가 이르러","자비로 그 몸 찢기시고","구주 예수 그리스도","보아라 즐거운 우리집","우리 모든 수고끝나","저 건너편 강언덕에","해지는 저편","저 뵈는 본향집","주가 맡긴 모든 역사","아름다운 본향","황무지가 장미꽃같이","저 요단강 건너편에","구원받은 천국의 성도들","저 좋은 낙원 이르니","나 가나안 땅 귀한 성에","보아라 저 하늘에","언약의 주 하나님","주 사랑하는 자 다 찬송할 때에","구주의 십자가 보혈로","놀랍다 주님의 큰 은혜","나의 죄를 씻기는","그 자비하신 주님","내 주의 보혈은","너희죄 흉악하나","나의 죄 모두 지신 주님","마음에 가득한 의심을 깨치고","샘물과 같은 보혈은","예수 십자가에 흘린 피로써","우리를 죄에서 구하시려","이 세상의 모든 죄를","날 구원하신 예수님","이 세상 험하고","정결하게 하는 샘이","주 십자가를 지심으로","주의 피로 이룬 샘물","주의 확실한 약속의 말씀듣고","죄에서 자유를 얻게 함은","그 참혹한 십자가에","변챦는 주님의 사랑과","나와 같은 죄인위해","고통의 멍에 벗으려고","나 주를 멀리 떠났다","나 행한것 죄 뿐이니","날마다 주와 멀어져","아버지여 이 죄인을","양떼를 떠나서","여러해 동안 주 떠나","인애하신 구세주여","천부여 의지 없어서","요나처럼 순종않고","큰 죄에 빠진 날 위해","나 속죄함을 받은 후","오랫동안 모든 죄 가운데 빠져","주의 말씀 받은 그 날","주 예수님 내 맘에 오사","예수 앞에 나오면","예수를 나의 구주삼고","주 예수 내맘에 들어와","우리는 주님을 늘 배반하나","외롭게 사는이 그 누군가","주 없이 살 수 없네","주의 사랑 비칠때에","하나님은 외아들을","큰 죄에 빠진 나를","죄인 구원하시려고","양 아흔 아홉 마리는","속죄하신 구세주를","하나님 사랑은","내 맘이 낙심되며","지금까지 지내온 것","내 주 하나님 넓고 큰 은혜는","날 위하여 십자가의","그 크신 하나님의 사랑","나 같은 죄인 살리신","죽을 죄인 살려주신","소리없이 보슬 보슬","내 평생 살아온 길","목마른 내 영혼","아 하나님의 은혜로","내 너를 위하여","너 하나님께 이끌리어","내 임금 예수 내 주여","내 구주 예수를 더욱 사랑","내 주 되신 주를 참 사랑하고","주여 나의 생명","내 주 예수 주신 은혜","순교자의 흘린피가","말씀으로 이 세상을","나의 죄를 정케하사","날 대속하신 예수께","세상의 헛된 신을 버리고","부름받아 나선 이 몸","예수 나를 오라하네","예수가 함께 계시니","내 죄를 회개하고","주님 주실 화평","너 주의 사람아","주 날 불러 이르소서","어둔밤 쉬 되리니","영광을 받으신 만유의 주여","우리는 부지런한","충성하라 죽도록","위대하신 주를","크고 놀라운 평화가","환난과 핍박 중에도","내 모든 시험 무거운 짐을","내 주를 가까이 하게 함은","내 주님지신 십자가","어지러운 세상 중에","십자가를 내가 지고","너 시험을 당해","시험을 받을때에","믿음으로 가리라","캄캄한 밤 사나운 바람불 때","주 예수 우리 구하려","허락하신 새 땅에","마귀들과 싸울지라","나는 예수 따라가는","우리들이 싸울것은","믿는 사람들은 주의 군사니","십자가 군병들아","십자가 군병되어서","주를 앙모하는 자","다같이 일어나","주 예수 이름 소리 높여","주 믿는사람 일어나","주의 진리 위해 십자가 군기","천성을 향해 가는 성도들아","행군 나팔 소리에","기도하는 이 시간","주여 복을 주시기를","내가 깊은 곳에서","내 기도하는 그 시간","마음속에 근심있는 사람","어두운 내 눈 밝히사","인내하게 하소서 주여 우리를","주 예수여 은혜를","죄짐 맡은 우리 구주","주 안에 있는 나에게","구주여 광풍이 불어","그 누가 나의 괴롬알며","고요한 바다로","나의 믿음 약할 때","나는 갈 길 모르니","나그네와 같은 내가","전능하신 주 하나님","내 선한 목자","내 갈 길 멀고 밤은 깊은데","나의 생명 되신 주","나 캄캄한 밤 죄의 길에","너 근심 걱정 말아라","눈을 들어 산을 보니","나의 갈길 다 가도록","못 박혀 죽으신","만세 반석 열린 곳에","멀리 멀리 갔더니","비바람이 칠때와","내게로 오라 하신 주님의","예수가 거느리시니","오 놀라운 구세주","주여 어린 사슴이","오 신실하신 주","이 세상의 친구들","자비하신 예수여","우리 주님 밤새워","주 사랑 안에 살면","어둠의 권세에서","어린양들아 두려워 말아라","험한 시험 물속에서","주의 곁에 있을때","나의 반석 나의 방패","영원하신 주님의","바다에 놀이 일때에","주의 친절한 팔에 안기세","곤한 내 영혼 편히 쉴곳과","구주와 함께 나 죽었으니","나 어느 곳에 있든지","나의 기쁨은 사랑의 주님께","내 맘에 한 노래 있어","아 내 맘속에","내 영혼의 그윽히 깊은데서","내 평생에 가는 길","이 세상은 요란하나","십자가 그늘 아래","너희 근심 걱정을","주 예수 넓은 품에","기쁠 때나 슬플 때나","주 날개 밑 내가 편안히 쉬네","너 성결키 위해","내가 예수 믿고서","거룩하게 하소서","먹보다도 더 검은","아버지여 나의 맘을","주님의 뜻을 이루소서","이 죄인을 완전케 하시옵고","맘 가난한 사람","내 영혼에 햇빛 비치니","세상 모든 풍파 너를 흔들어","주와 같이 길 가는것","주 안에 기쁨있네","큰 물결이 설레는 어둔 바다","귀하신 주여 날 붙드사","귀하신 친구 내게 계시니","나의 영원하신 기업","나 이제 주님의 새생명 얻은몸","하늘 보좌 떠나서","내 영혼이 은총 입어","십자가로 가까이","어디든지 예수 나를 이끌면","은혜 구한 내게 은혜의 주님","저 장미꽃 위에 이슬","아침 햇살 비칠 때","겟세마네 동산에서","태산을 넘어 험곡에 가도","주 음성 외에는","이 세상 끝날까지","주님 가신 길을 따라","예수 따라가며","내 평생 소원 이것뿐","예수 영광 버리사","내 모든 소원 기도의 제목","예수 더 알기 원하네","주와 같이 되기를","주님의 마음을 본받는 자","거친 세상에서 실패하거든","겟세마네 동산의","너희 마음에 슬픔이 가득할 때","누가 주를 따라","뜻없이 무릎 꿇는","십자가를 질 수 있나","생명 진리 은혜 되신","신자 되기 원합니다","믿음의 새빛을","주 믿는 나 남 위해","죽기까지 사랑하신 주","높으신 주께서 낮아지심은","큰 사랑의 새 계명을","내 주 하나님","나의 몸이 상하여","주여 나의 병든 몸을","네 병든 손 내밀라고","괴로움과 고통을","의원되신 예수님의","인류는 하나되게","꽃이 피고 새가 우는","하나님이 창조하신","참 아름다워라","괴로운 인생길 가는 몸이","천국에서 만나보자","때 저물어서 날이 어두니","참 즐거운 노래를","구름 같은 이 세상","내 맘의 주여 소망되소서","세월이 흘러 가는데","이 세상에 근심된 일이 많고","어두움 후에 빛이 오며","이 몸의 소망 무언가","저 요단강 건너편에 찬란하게","주여 지난 밤 내 꿈에","저 높은 곳을 향하여","잠시 세상에 내가 살면서","하늘가는 밝은 길이","만세 반석 열리니","익은곡식 거둘자가","새벽부터 우리","주 예수 넓은 사랑","저 죽어가는 자 다 구원하고","흑암에 사는 백성들을 보라","물 위에 생명줄 던지어라","너 시온아 이 소식 전파하라","빛의 사자들이여","세상 모두 사랑 없어","주님의 명령 전할 사자여","온 세상 위하여","땅끝까지 복음을","저 북방 얼음산과","우리가 지금은 나그네 되어도","기쁜 일이 있어 천국 종 치네","하나님의 진리 등대","예수 말씀하시기를","천성길을 버리고","헛된 욕망 길을 가며","먼동 튼다 일어나라","눈을 들어 하늘 보라","옳은 길 따르라 의의 길을","가난한 자 돌봐주며","기쁜 소리 들리니","구주께서 부르되","듣는 사람마다 복음 전하여","구원으로 인도하는","웬일인가 내 형제여","어둔 죄악 길에서","갈 길을 밝히 보이시니","돌아와 돌아와","목마른 자들아","어서 돌아오오","예수가 우리를 부르는 소리","온유한 주님의 음성","주께서 문에 오셔서","자비한 주께서 부르시네","주께로 한 걸음씩","우리 주 십자가","주님 찾아오셨네","주 예수 대문 밖에","죄짐에 눌린 사람은","형제여 지체 말라","죄짐을 지고서 곤하거든","너 예수께 조용히 나가","주의 음성을 내가 들으니","꽃이 피는 봄날에만","구주 예수 의지함이","어려운 일 당할때","울어도 못하네","이 눈에 아무 증거 아니 뵈어도","주님 약속하신 말씀위에 서","나 같은 죄인까지도","날 구속하신 주께","내 주여 뜻대로","시온의 영광이 빛나는 아침","오늘까지 복과 은혜","아침 해가 돋을 때","새해 아침 환희 밝았네","종소리 크게 울려라","우리 주님 모신 가정","날마다 주님을 의지하는","에덴의 동산처럼","미더워라 주의 가정","사철에 봄바람 불어 잇고","주의 발자취를 따름이","예수님의 사랑은","예루살렘 아이들","예수 사랑하심을","예수께서 오실 때에","예수께로 가면","사랑의 하나님 귀하신 이름은","다정하신 목자 예수","하나님은 나의 목자시니","선한 목자 되신 우리 주","주는 나를 기르시는 목자","역사 속에 보냄 받아","바다같이 넓은 은혜","말씀에 순종하여","가슴마다 파도 친다","주님께 귀한것 드려","하나님의 뜻을 따라","낳으시고 길러주신","언제나 바라봐도","어머니의 넓은 사랑","삼천리 반도 금수강산","주 하나님 이 나라를 지켜주시고","어둔 밤 마음에 잠겨","이 민족에 복음을","우리나라 지켜주신","내 주는 강한 성이요","어느 민족 누구게나","감사하는 성도여","공중 나는 새를 보라","넓은 들에 익은 곡식","논밭에 오곡백과","저 밭에 농부 나가","산마다 불이 탄다 고운 단풍에","아름다운 하늘과","감사하세 찬양하세","나 맡은 본분은","영광은 주님 홀로","이전에 주님을 내가 몰라","천지 주관하는 주님","우리의 기도 들어주시옵소서","교회의 참된 터는","하나님이 정하시고","성부님께 빕니다","태초에 하나님이","완전한 사랑","오늘 모여 찬송함은","해보다 더 밝은 저 천국","내 본향 가는 길 보이도다","후일에 생명 그칠 때","이 세상 살때에","고생과 수고가 다 지난후","주님 오라 부르시어","이 땅에서 주를 위해","사랑의 주 하나님","얼마나 아프셨나","그 큰 일을 행하신","주를 경배하리","주님을 찬양합니다","나 주님을 사랑합니다","놀라운 그 이름","여기에 모인 우리","찬양하라 내 영혼아","거룩한 밤","주님의 시간에","우리 모두 찬양해","거룩 거룩 거룩한 하나님","만민들아 다 경배하라","할렐루야 할렐루야 다 함께","아멘 아멘 아멘 영광과 존귀를","거룩 거룩 거룩","진리와 생명 되신 주","우리 기도를","주여 주여 우리를","나의 하나님 받으소서","모든 것이 주께로부터","하늘에 계신","하늘에 계신","주님 우리의 마음을 여시어","주 너를 지키시고","주 함께 하소서","아멘","아멘","아멘","아멘","아멘","아멘",blank};

    static int [] sortedNumbers = {136,517,574,153,524,150,587,594,211,182,147,629,8,625,422,52,128,48,77,622,107,42,45,456,444,157,457,212,610,110,373,109,62,272,406,104,588,479,473,600,483,26,244,521,519,85,371,234,542,407,250,111,152,207,433,434,127,372,112,108,114,253,269,304,615,361,159,64,115,518,509,418,476,541,246,547,305,376,375,349,595,283,408,134,271,384,95,409,83,470,374,402,199,380,213,435,252,320,256,633,436,618,273,214,381,274,548,262,321,556,275,303,577,363,86,191,421,379,389,50,314,364,311,73,410,484,300,452,337,607,378,41,65,428,412,438,313,326,215,585,170,87,339,315,338,549,317,208,254,469,302,88,308,450,413,382,420,501,342,539,328,312,416,458,255,589,218,472,590,619,251,467,459,383,515,66,355,131,567,21,12,173,200,174,525,116,520,506,481,460,100,348,129,365,257,626,117,1,494,386,151,72,22,32,23,573,319,427,156,423,514,387,25,634,309,526,6,385,160,188,500,558,351,344,464,572,404,270,247,235,28,323,181,162,184,187,388,183,27,502,613,17,566,220,172,169,559,592,580,496,553,258,154,462,89,158,569,18,29,226,193,196,190,195,602,7,3,4,82,216,53,503,429,322,485,307,298,318,91,210,142,550,343,175,463,225,353,352,415,439,341,461,155,166,106,411,241,593,640,641,642,643,644,645,628,229,424,276,552,443,310,277,297,124,586,366,487,582,330,523,398,440,543,399,579,527,135,340,248,578,614,557,620,278,571,118,33,596,331,67,403,102,13,60,186,148,562,71,390,528,325,565,564,324,144,93,98,96,561,453,449,288,511,164,563,259,287,451,38,119,145,228,391,551,605,57,177,105,284,74,120,393,139,68,505,84,529,69,516,604,140,24,291,281,61,508,121,631,584,332,290,231,222,350,260,624,236,599,56,230,555,396,103,533,544,143,522,334,92,232,197,441,16,474,185,46,545,612,488,583,101,447,609,486,414,261,394,209,263,597,426,5,495,367,475,279,192,233,395,531,492,237,78,491,123,146,591,239,507,113,243,489,442,245,498,194,10,30,377,264,224,97,554,268,296,369,536,538,240,63,532,530,419,329,638,81,570,448,575,165,99,546,611,637,617,206,425,455,504,623,51,327,534,149,616,354,465,357,221,397,249,265,431,370,227,176,292,471,316,54,362,392,75,632,490,90,289,497,417,286,535,178,94,368,346,179,36,37,356,205,198,430,454,14,39,446,401,204,285,560,293,132,540,358,405,266,267,55,219,581,79,639,466,306,43,167,301,58,44,630,189,130,40,19,2,621,31,34,122,201,478,482,76,480,280,125,126,512,359,80,598,333,345,335,432,468,20,35,295,282,445,603,70,299,59,202,568,294,223,180,171,576,133,203,137,510,15,217,49,601,477,493,437,9,635,636,168,47,161,163,627,606,238,138,360,347,400,513,537,141,11,336,242,608,499,0};

//    static String [] Kyodoks = {blank,"시편 1편","시편 2편","시편 4편","시편 5편","시편 8편","시편 10편","시편 13편","시편 14편","시편 15편","시편 16편","시편 17편","시편 19편","시편 23편","시편 24편","시편 27편","시편 28편","시편 29편","시편 31편","시편 32편","시편 33편","시편 34편","시편 37편","시편 43편","시편 46편","시편 47편","시편 50편","시편 51편","시편 63편","시편 65편","시편 67편","시편 68편","시편 71편","시편 72편","시편 81편","시편 84편","시편 90편","시편 91편","시편 92편","시편 95편","시편 96편","시편 97편","시편 98편","시편 99편","시편 100편","시편 103편","시편 104편","시편 105편","시편 106편","시편 108편","시편 116편","시편 118편","시편 119편","시편 121편","시편 126편","시편 127편","시편 128편","시편 130편","시편 133편","시편 136편","시편 139편","시편 142편","시편 143편","시편 145편","시편 148편","시편 149편","시편 150편","잠언 3장","이사야 40장","이사야 40장","이사야 42장","이사야 55장","이사야 58장","이사야 65장","마태복음 5장","마태복음 6장","요한복음 1장","요한복음 3장","요한복음 14장","요한복음 15장","고린도후서 4장","에베소서 4장","빌립보서 2장","빌립보서 4장","히브리서 11장","요한1서 4장","요한계시록 14장","요한계시록 21장","세례 [1]","세례 [2]","세례 [3]","성찬 [1]","성찬 [2]","새해 [1]","새해 [2]","가정주일","어린이주일","청년주일","어버이주일","나라 사랑 [1]","나라 사랑 [2]","나라 사랑 [3]","나라사랑 [4]","나라 사랑 [5]","종교개혁주일","감사절 [1]","감사절 [2]","임직식 [1]","임직식 [2]","헌당예배","선교주일","성서주일","교회교육주일","자연과 환경","이웃 사랑","구주 강림 [1]","구주 강림 [2]","구주 강림 [3]","구주 강림 [4]","성탄절 [1]","성탄절 [2]","주현절 [1]","주현절 [2]","주현절 [3]","사순절 [1]","사순절 [2]","사순절 [3]","사순절 [4]","사순절 [5]","종려주일","고난주간 [1]","고난주간 [2]","고난주간 [3]","부활절 [1]","부활절 [2]","성령 강림 [1]","성령강림 [2]","삼위일체",blank};

}
