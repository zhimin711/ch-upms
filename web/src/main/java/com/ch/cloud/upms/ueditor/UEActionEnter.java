package com.ch.cloud.upms.ueditor;

import com.baidu.ueditor.ActionEnter;
import com.baidu.ueditor.ConfigManager;
import com.baidu.ueditor.define.ActionMap;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author 01370603
 * @since 2018/12/24 11:43
 */
public class UEActionEnter extends ActionEnter {

    private HttpServletRequest request;

    private String rootPath;
    private String contextPath;

    private String actionType;

    private ConfigManager configManager;

    public UEActionEnter(HttpServletRequest request, String rootPath) {
        super(request, rootPath);
        this.request = request;
        this.rootPath = rootPath;
        this.actionType = request.getParameter("action");
        this.contextPath = request.getContextPath();
        this.configManager = ConfigManager.getInstance(this.rootPath, this.contextPath, request.getRequestURI());
    }

    @Override
    public String invoke() {
        if (actionType == null || !ActionMap.mapping.containsKey(actionType)) {
            return new BaseState(false, AppInfo.INVALID_ACTION).toJSONString();
        }
        if (this.configManager == null || !this.configManager.valid()) {
            return new BaseState(false, AppInfo.CONFIG_ERROR).toJSONString();
        }
        int actionCode = ActionMap.getType(this.actionType);
        State state;
        Map<String, Object> conf;

        switch (actionCode) {
            case ActionMap.UPLOAD_IMAGE:
            case ActionMap.UPLOAD_SCRAWL:
            case ActionMap.UPLOAD_VIDEO:
            case ActionMap.UPLOAD_FILE:
                conf = this.configManager.getConfig(actionCode);
                state = new UEUploader(request, conf).doExec();
                break;
            default:
                return super.invoke();
        }

        return state.toJSONString();
    }


}
