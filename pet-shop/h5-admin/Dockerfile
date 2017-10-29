# nginx
FROM nginx:latest
WORKDIR /etc/nginx
ADD cert cert
ADD conf.d conf.d
ADD public /var/www
EXPOSE 80
EXPOSE 443
CMD ["nginx","-g","daemon off;"]
