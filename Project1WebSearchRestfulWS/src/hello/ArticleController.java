package hello;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demo.DisplayArticle;
import domain.Posting;

@RestController
public class ArticleController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@RequestMapping("/article")
	public Posting greeting(@RequestParam(value = "name", defaultValue = "123") String name) {
//		return new Greeting(counter.incrementAndGet(), String.format(template,
//				name));
		return DisplayArticle.displayArticle(name);
	}
}