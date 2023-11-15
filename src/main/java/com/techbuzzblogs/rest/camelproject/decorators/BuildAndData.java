package com.techbuzzblogs.rest.camelproject.decorators;

import com.techbuzzblogs.rest.camelproject.utils.Console;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({TYPE, METHOD, CONSTRUCTOR})
@Import({ Console.class })
@Retention(RetentionPolicy.SOURCE)
public @interface BuildAndData {
}
