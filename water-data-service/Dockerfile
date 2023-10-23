FROM node:21-alpine

ENV POSTGRES_PASSWORD=password \
  POSTGRES_DB=water_sensor

RUN mkdir -p /home/app

COPY ./app /home/app

EXPOSE 5000

CMD ["node", "./home/app/app.js"]