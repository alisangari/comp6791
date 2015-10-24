package hello;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demo.Search;

@RestController
public class SearchController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@RequestMapping("/search")
	public ArrayList<String> greeting(@RequestParam(value = "name", defaultValue = "123") String name) {
//		return new Greeting(counter.incrementAndGet(), String.format(template,
//				name));
		try {
			return Search.search(name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		return new ArrayList<>();
	}
}