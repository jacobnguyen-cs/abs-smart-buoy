FROM python:3-alpine

COPY ./requirements.txt /app/requirements.txt
COPY ./run.py /app
WORKDIR /app
RUN pip install --no-cache-dir -r requirements.txt

EXPOSE 5000

ENTRYPOINT ["python"]
CMD ["run.py"]