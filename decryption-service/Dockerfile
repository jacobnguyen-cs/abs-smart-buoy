FROM python:3-alpine

COPY ./requirements.txt /app/requirements.txt
COPY ./run.py /app
COPY ./.env /app
WORKDIR /app
RUN pip install --no-cache-dir -r requirements.txt

EXPOSE 6000

ENTRYPOINT ["python"]
CMD ["run.py"]