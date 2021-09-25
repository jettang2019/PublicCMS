package com.publiccms.controller.admin.cms;

import java.util.ArrayList;
import java.util.List;

// Generated 2020-7-1 21:06:19 by com.publiccms.common.generator.SourceGenerator

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.publiccms.common.annotation.Csrf;
import com.publiccms.common.constants.CommonConstants;
import com.publiccms.common.tools.CommonUtils;
import com.publiccms.common.tools.JsonUtils;
import com.publiccms.common.tools.RequestUtils;
import com.publiccms.entities.cms.CmsSurveyQuestion;
import com.publiccms.entities.cms.CmsSurveyQuestionItem;
import com.publiccms.entities.log.LogOperate;
import com.publiccms.entities.sys.SysSite;
import com.publiccms.entities.sys.SysUser;
import com.publiccms.logic.service.cms.CmsSurveyQuestionItemService;
import com.publiccms.logic.service.cms.CmsSurveyQuestionService;
import com.publiccms.logic.service.log.LogLoginService;
import com.publiccms.logic.service.log.LogOperateService;
import com.publiccms.views.pojo.entities.QuestionItem;
import com.publiccms.views.pojo.model.CmsSurveyQuestionParameters;

/**
 *
 * CmsSurveyQuestionAdminController
 * 
 */
@Controller
@RequestMapping("cmsSurveyQuestion")
public class CmsSurveyQuestionAdminController {

    private String[] ignoreProperties = new String[] { "id", "votes" };
    private String[] itemIgnoreProperties = new String[] { "id", "questionId", "votes" };

    /**
     * @param site
     * @param admin
     * @param entity
     * @param questionParameters
     * @param request
     * @param model
     * @return operate result
     */
    @RequestMapping("save")
    @Csrf
    public String save(@RequestAttribute SysSite site, @SessionAttribute SysUser admin, CmsSurveyQuestion entity,
            @ModelAttribute CmsSurveyQuestionParameters questionParameters, HttpServletRequest request, ModelMap model) {
        List<CmsSurveyQuestionItem> itemList = new ArrayList<>();
        if (ArrayUtils.contains(CmsSurveyQuestionService.QUESTION_TYPES_DICT, entity.getQuestionType())) {
            entity.setAnswer(null);
            if (CommonUtils.notEmpty(questionParameters.getItemList())) {
                StringBuilder answer = new StringBuilder();
                for (QuestionItem item : questionParameters.getItemList()) {
                    if (item.isAnswer()) {
                        if (answer.length() > 0) {
                            if (CmsSurveyQuestionService.QUESTION_TYPE_CHECKBOX.equalsIgnoreCase(entity.getQuestionType())) {
                                answer.append(CommonConstants.COMMA);
                                answer.append(item.getId());
                            }
                        } else {
                            answer.append(item.getId());
                        }
                    }
                    CmsSurveyQuestionItem temp = new CmsSurveyQuestionItem(item.getQuestionId(), 0, item.getTitle(),
                            item.getSort());
                    temp.setId(item.getId());
                    itemList.add(temp);
                }
                if (answer.length() > 0) {
                    entity.setAnswer(answer.toString());
                }
            }
        }
        if (null != entity.getId()) {
            entity = service.update(entity.getId(), entity, ignoreProperties);
            itemService.update(entity.getId(), itemList, itemIgnoreProperties);
            logOperateService.save(new LogOperate(site.getId(), admin.getId(), admin.getDeptId(),
                    LogLoginService.CHANNEL_WEB_MANAGER, "update.cmsSurveyQuestion", RequestUtils.getIpAddress(request),
                    CommonUtils.getDate(), JsonUtils.getString(entity)));
        } else {
            service.save(entity);
            itemService.save(entity.getId(), itemList);
            logOperateService.save(new LogOperate(site.getId(), admin.getId(), admin.getDeptId(),
                    LogLoginService.CHANNEL_WEB_MANAGER, "save.cmsSurveyQuestion", RequestUtils.getIpAddress(request),
                    CommonUtils.getDate(), JsonUtils.getString(entity)));
        }
        return CommonConstants.TEMPLATE_DONE;
    }

    /**
     * @param ids
     * @param request
     * @param site
     * @param admin
     * @param model
     * @return operate result
     */
    @RequestMapping("delete")
    @Csrf
    public String delete(@RequestAttribute SysSite site, @SessionAttribute SysUser admin, Long[] ids, HttpServletRequest request,
            ModelMap model) {
        if (CommonUtils.notEmpty(ids)) {
            service.delete(ids);
            logOperateService.save(new LogOperate(site.getId(), admin.getId(), admin.getDeptId(),
                    LogLoginService.CHANNEL_WEB_MANAGER, "delete.cmsSurveyQuestion", RequestUtils.getIpAddress(request),
                    CommonUtils.getDate(), StringUtils.join(ids, ',')));
        }
        return CommonConstants.TEMPLATE_DONE;
    }

    @Autowired
    private CmsSurveyQuestionService service;
    @Autowired
    private CmsSurveyQuestionItemService itemService;
    @Autowired
    protected LogOperateService logOperateService;
}