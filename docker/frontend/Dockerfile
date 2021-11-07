FROM node:14.18.1-alpine
WORKDIR /opt/aviasale
COPY /frontend/ .
RUN npm install
EXPOSE 8080
CMD ["npm", "run", "serve"]