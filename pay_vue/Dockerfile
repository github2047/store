# 基础镜像
FROM nginx:mainline-alpine
# 将文件添加到镜像中
COPY dist/ /usr/share/nginx/html
# 暴露端口
EXPOSE 80
# 启动容器后需要运行的服务
# ENTRYPOINT ["nginx"]
# 可以不写，基础镜像已经写了运行的服务
CMD ["nginx", "-g", "daemon off;"] 