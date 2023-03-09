package com.wubaoguo.springboot.manage.controller;


import com.wubaoguo.springboot.core.request.ViewResult;
import com.wubaoguo.springboot.entity.sys.SysDictionary;
import com.wubaoguo.springboot.manage.controller.commond.CondCacheCommond.CondCacheCommond;
import com.wubaoguo.springboot.manage.controller.commond.CondCacheCommond.QueryCommondSession;
import com.wubaoguo.springboot.manage.service.SysDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/manage/sysdictionary")
public class SysDictionaryController {

    @Autowired
    private SysDictionaryService sysDictionaryService;

    @ModelAttribute
    public void populateModel(CondCacheCommond commond, Model model) {
        commond = QueryCommondSession.validateCommond(this.getClass().getName(), commond);
        model.addAttribute("commond", commond);
        QueryCommondSession.putQueryCommond(this.getClass().getName(), commond);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String form(@PathVariable("id") String id, ModelMap map) {
        SysDictionary sysDictionary = new SysDictionary();
        if (!"none".equals(id)) {
            sysDictionary = sysDictionary.setId(id).queryForBean();
        }
        map.addAttribute("entity", sysDictionary);
        return "/manage/dictionary/form";
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(ModelMap map) {
        return "/manage/dictionary/main";
    }


    @RequestMapping(value = "/table", method = RequestMethod.GET)
    public String findSysDictionary(CondCacheCommond commond, ModelMap map) {
        commond = QueryCommondSession.validateCommond(this.getClass().getName(), commond);
        map.put("entitys", sysDictionaryService.findSysDictionaryByComond(commond));
        QueryCommondSession.putQueryCommond(this.getClass().getName(), commond);
        return "/manage/dictionary/table";
    }


    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public String save(SysDictionary sysDictionary) {
        return ViewResult.newInstance().state(sysDictionaryService.saveSysDictionary(sysDictionary)).json();
    }

    @ResponseBody
    @RequestMapping(value = "/inline", method = RequestMethod.POST)
    public String update(String id, String column, String value) {
        return sysDictionaryService.updateSysDictionary(id, column, value);
    }
}
