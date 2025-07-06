package io.github.quizmeup.sdk.eventflow.core.service;

import io.github.quizmeup.sdk.eventflow.core.domain.handler.CommandHandler;
import io.github.quizmeup.sdk.eventflow.core.domain.handler.EventHandler;
import io.github.quizmeup.sdk.eventflow.core.domain.handler.EventSourcingHandler;
import io.github.quizmeup.sdk.eventflow.core.domain.handler.QueryHandler;
import io.github.quizmeup.sdk.eventflow.core.domain.topic.MessageTopic;
import io.github.quizmeup.sdk.eventflow.core.service.dispatcher.CommandDispatcher;
import io.github.quizmeup.sdk.eventflow.core.service.dispatcher.EventDispatcher;
import io.github.quizmeup.sdk.eventflow.core.service.dispatcher.QueryDispatcher;
import io.github.quizmeup.sdk.eventflow.core.port.HandlerRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HandlerServiceTest {

    @Mock
    private TopicService topicService;

    @Mock
    private HandlerRegistry handlerRegistry;

    @Mock
    private EventDispatcher eventDispatcher;

    @Mock
    private QueryDispatcher queryDispatcher;

    @Mock
    private CommandDispatcher commandDispatcher;

    @InjectMocks
    private HandlerService handlerService;

    private EventHandler mockEventHandler;
    private QueryHandler mockQueryHandler;
    private CommandHandler mockCommandHandler;
    private EventSourcingHandler mockEventSourcingHandler;

    @BeforeEach
    void setUp() {
        mockEventHandler = mock(EventHandler.class);
        mockQueryHandler = mock(QueryHandler.class);
        mockCommandHandler = mock(CommandHandler.class);
        mockEventSourcingHandler = mock(EventSourcingHandler.class);
    }

    @Test
    void register_eventHandler_shouldRegisterAndSubscribe() {
        doReturn(String.class).when(mockEventHandler).payloadClass();
        doReturn(new Object()).when(mockEventHandler).instance();

        handlerService.register(mockEventHandler);

        verify(handlerRegistry).registerHandler(mockEventHandler);
        verify(eventDispatcher).subscribeTo(any(MessageTopic.class), any(String.class));
        verify(topicService).save(any(MessageTopic.class));
    }

    @Test
    void register_queryHandler_shouldRegisterAndSubscribe() {
        doReturn(Integer.class).when(mockQueryHandler).payloadClass();
        doReturn(new Object()).when(mockQueryHandler).instance();

        handlerService.register(mockQueryHandler);

        verify(handlerRegistry).registerHandler(mockQueryHandler);
        verify(queryDispatcher).subscribeTo(any(MessageTopic.class), any(String.class));
        verify(topicService).save(any(MessageTopic.class));
    }

    @Test
    void register_commandHandler_shouldRegisterAndSubscribe() {
        doReturn(Double.class).when(mockCommandHandler).payloadClass();
        doReturn(new Object()).when(mockCommandHandler).instance();

        handlerService.register(mockCommandHandler);

        verify(handlerRegistry).registerHandler(mockCommandHandler);
        verify(commandDispatcher).subscribeTo(any(MessageTopic.class), any(String.class));
        verify(topicService).save(any(MessageTopic.class));
    }

    @Test
    void register_eventSourcingHandler_shouldRegister() {
        doReturn(Long.class).when(mockEventSourcingHandler).payloadClass();

        doReturn(new Object()).when(mockEventSourcingHandler).instance();

        handlerService.register(mockEventSourcingHandler);

        verify(handlerRegistry).registerHandler(mockEventSourcingHandler);
        verify(topicService).save(any(MessageTopic.class));
        verifyNoInteractions(eventDispatcher, queryDispatcher, commandDispatcher);
    }

}
