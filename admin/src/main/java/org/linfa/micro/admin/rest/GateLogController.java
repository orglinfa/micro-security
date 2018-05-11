package org.linfa.micro.admin.rest;

import org.linfa.micro.admin.biz.GateLogBiz;
import org.linfa.micro.admin.entity.GateLog;
import org.linfa.micro.common.rest.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("gateLog")
public class GateLogController extends BaseController<GateLogBiz,GateLog> {
}
