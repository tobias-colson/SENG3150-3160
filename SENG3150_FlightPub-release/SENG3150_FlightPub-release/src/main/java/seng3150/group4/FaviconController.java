package seng3150.group4;

/*
  This stops the spam of warnings for favicon
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FaviconController
{
    @RequestMapping("favicon.ico")
    @ResponseBody
    void favicon() {}
}
