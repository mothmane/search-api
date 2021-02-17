package ma.toto.search.helpers;

import ma.toto.search.controller.SearchController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("example-entities")
public class ExampleEntityController extends SearchController {}
