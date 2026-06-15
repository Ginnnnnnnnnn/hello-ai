# 🍃 Spring AI / Spring AI Alibaba

Spring AI 和 Spring AI Alibaba 是为 Java 开发者设计、用于构建 AI 应用的框架。Spring AI 是基础，提供了连接各类
AI 模型的核心标准。Spring AI Alibaba 在 Spring AI 的基础上，提供了更强大的智能体、工作流编排以及阿里云深度集成能力。

### 流式输出

流式输出就是不需要等全部内容都返回后一起展示出来，而是可以在过程中一部分一部分的展示出来。现在主流的AI对话工具都是用的流式输出的方式，这样可以减少用户的等待时间，用户体验更好。

- Http Stream
- SSE
- Flux

### ChatModel

专门和对话模型对接的对象。定义了与对话功能的语言模型交互的统一方式。

### ChatClient

一个更高级、更简洁的工具。

### 提示词

- 角色/规则定义 ( role )
- 少量示例 ( few shot )
- 输出格式
- 指定思考
- 思维链

### 提示词模板

- 提示词模板
- 占位符
- 模板文件