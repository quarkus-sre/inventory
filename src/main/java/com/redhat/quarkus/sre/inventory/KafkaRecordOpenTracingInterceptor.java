package com.redhat.quarkus.sre.inventory;

import java.util.Optional;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.kafka.common.header.Headers;
import org.eclipse.microprofile.opentracing.Traced;
import org.eclipse.microprofile.reactive.messaging.Message;

import io.opentracing.SpanContext;
import io.opentracing.contrib.kafka.TracingKafkaUtils;
import io.opentracing.util.GlobalTracer;
import io.smallrye.opentracing.contrib.interceptor.OpenTracingInterceptor;
import io.smallrye.reactive.messaging.kafka.api.IncomingKafkaRecordMetadata;

@Traced
@Interceptor
public class KafkaRecordOpenTracingInterceptor {

    @AroundInvoke
    @SuppressWarnings("all")
    public Object propagateSpanCtx(InvocationContext ctx) throws Exception {
        for (int i = 0 ; i < ctx.getParameters().length ; i++) {
            Object parameter = ctx.getParameters()[i];

            if (parameter instanceof Message) {
                Message message = (Message) parameter;

                Optional<IncomingKafkaRecordMetadata<?, ?>> metadata = message.getMetadata(IncomingKafkaRecordMetadata.class);

                Headers headers = (Headers)metadata
                    .map(m -> m.getHeaders())
                    .get();
                SpanContext spanContext = getSpanContext(headers);
                ctx.getContextData().put(OpenTracingInterceptor.SPAN_CONTEXT, spanContext);
            }
        }

        return ctx.proceed();
    }

    private SpanContext getSpanContext(Headers headers) {
        return TracingKafkaUtils.extractSpanContext(headers, GlobalTracer.get());
    }
}