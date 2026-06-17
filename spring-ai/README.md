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

### 结构化输出

让大模型按照我们要求的合适来做输出。比较常用的格式就是JSON，因为他最灵活。

### 对话记忆

- MessageList  
  使用 List 存放所有 message，每次全部传递给大模型。
- ChatMemory  
  框架内部实现，只需要传递 chatId 即可实现对话记忆，可以手动创建，也可以引入依赖注入。
- 持久化记忆
    - JdbcChatMemoryRepository  
      使用 JDBC 将消息存储在关系型数据库中，它支持多种数据库，PostgreSQL、MySQL / MariaDB、SQL Server、Oracle等
    - CassandraChatMemoryRepository  
      使用 Apache Cassandra 存储消息。它适用于需要持久化存储聊天记录的应用，特别是在需要高可用性、持久性、可扩展性，以及利用TTL功能时。
    - Neo4jChatMemoryRepository
      使用 Neo4j 将聊天消息存储为属性图数据库中的节点和关系。它适用于希望利用 Neo4j 的图功能进行聊天记忆持久化的应用程序。
    - CosmosDBChatMemoryRepository  
      使用 Azure Cosmos DB NoSQL API 来存储消息。它适用于需要全球分布式、高度可扩展的文档数据库来持 久化聊天内存的应用程序。该存储库使用对话
      ID 作为分区键，以确保高效的数据分布和快速检索。
    - MongoChatMemoryRepository  
      使用 MongoDB 存储消息。它适用于需要灵活的、面向文档的数据库进行聊天内存持久化的应用程序。

