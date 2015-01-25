package com.zc.addmony.common;

import java.util.HashMap;
import java.util.Map;

import android.graphics.drawable.Drawable;

public class Constants {

	public static final String INTENT_ACTION_FINISH_ALL = "com.bsk.yun.intent.ACTION_FINISH_ALL";// �ر�����ҳ��Intent

	public static Map<String, Object> passValueMap = new HashMap<String, Object>();
	public static Drawable drawable;
	public static boolean isChange = false;

	/** ��ǰ DEMO Ӧ�õ� APP_KEY������Ӧ��Ӧ��ʹ���Լ��� APP_KEY �滻�� APP_KEY */
	public static final String APP_KEY = "2025482806";

	/**
	 * ��ǰ DEMO Ӧ�õĻص�ҳ������Ӧ�ÿ���ʹ���Լ��Ļص�ҳ��
	 * 
	 * <p>
	 * ע��������Ȩ�ص�ҳ���ƶ��ͻ���Ӧ����˵���û��ǲ��ɼ�ģ����Զ���Ϊ������ʽ������Ӱ�죬 ����û�ж��彫�޷�ʹ�� SDK ��֤��¼��
	 * ����ʹ��Ĭ�ϻص�ҳ��https://api.weibo.com/oauth2/default.html
	 * </p>
	 */
	public static final String REDIRECT_URL = "http://open.weibo.com/apps/2025482806/info/advanced";

	/**
	 * Scope �� OAuth2.0 ��Ȩ������ authorize �ӿڵ�һ������ͨ�� Scope��ƽ̨�����Ÿ���΢��
	 * ���Ĺ��ܸ��ߣ�ͬʱҲ��ǿ�û���˽�������������û����飬�û����� OAuth2.0 ��Ȩҳ����Ȩ�� ѡ����Ӧ�õĹ��ܡ�
	 * 
	 * ����ͨ������΢������ƽ̨-->��������-->�ҵ�Ӧ��-->�ӿڹ��?���ܿ�������Ŀǰ������Щ�ӿڵ� ʹ��Ȩ�ޣ��߼�Ȩ����Ҫ�������롣
	 * 
	 * Ŀǰ Scope ֧�ִ����� Scope Ȩ�ޣ��ö��ŷָ���
	 * 
	 * �й���Щ OpenAPI ��ҪȨ�����룬��鿴��http://open.weibo.com/wiki/%E5%BE%AE%E5%8D%9AAPI
	 * ���� Scope ���ע�������鿴��http://open.weibo.com/wiki/Scope
	 */
	public static final String SCOPE = "email,direct_messages_read,direct_messages_write,"
			+ "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
			+ "follow_app_official_microblog," + "invitation_write";

	public static int cid;
	public static boolean refresh = false;
	public static boolean pushInput = false;// ͨ�����ʹ����

}
