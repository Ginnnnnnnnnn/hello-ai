# ⛓️ LangChain4j

LangChain是一个强大的开源框架，专门用于开发基于大语言模型(LLMs)
的应用程序。它的主要目标是简化LLM应用程序的开发流程，提供了一套完整的工具和组件，使开发者能够更容易地构建复杂的AI应用。

## 低层次API

提供了如下Basics（大模型、提示词模版、模型记忆等）和RAG（向量模型、向量数据库、文本载入分割工具）两类低层次接口，开发者从而能够灵活的实现这些接口并根据自己的需求进行组合，定制化自己的大模型应用。

- Chat
- Stream
- 对话记忆
- 结构化输出
- 工具调用

## 高层次API

为了让Java开发者可以更加关注业务逻辑而不是这些底层实现，LangChain4J提供了两个高层次的API，Chains：包括Chains和AI
Services两种类别，Chains源于Langchain，相当于将低层次模块组合起来，形成一些固定的处理流程，并协调它们之间的交互。AI
Service：AI Services是LangChain4J为 Java 量身定制的解决方案，和Spring Data
JPA类似，只需要显示的定义接口，并且可以自定义的加入Memory、Tools或者RAG，具体调用逻辑实现由LangChain4j代理完成。

- Chat
- Stream
- 对话记忆
- 结构化输出
- 工具调用