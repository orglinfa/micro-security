package org.linfa.micro.admin.rest;

import org.linfa.micro.admin.biz.GroupTypeBiz;
import org.linfa.micro.admin.entity.GroupType;
import org.linfa.micro.common.rest.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("groupType")
public class GroupTypeController extends BaseController<GroupTypeBiz,GroupType> {
}
