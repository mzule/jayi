jayi
===
Jayi是一个静态网页生成器，将符合规范的文件，生成为全静态的html文件。支持include文件以及template.

如何使用
===
Jayi要求资源文件满足一定的格式，如下：

```
Jayi_project
    --+ _includes
        --+ top.html
        --+ bottom.html
    --+ _templates
        --+ post_template.html
    --+ _posts
        --+ THis is my first post via Jayi.html
    --+ index.html
```
1. _includes _includes
