# Use the official Nginx base image
# FROM bumory1987/flink:0.001
FROM bumory1987/flink:masterv1

ENTRYPOINT ["/bin/bash"]

# Set environment variables for Kafka
ENV MASTER=localhost
ENV WORKER=localhost
ENV MASTER_PORT=8081
ENV JAVA_HOME=/home/sung/jdk11
ENV PATH=$PATH:$JAVA_HOME/bin



# Switch to a non-root user if necessary
USER sung

# Set the working directory
WORKDIR /home/sung

# Copy custom configuration file if needed
# COPY nginx.conf /etc/nginx/nginx.conf/

# Run any necessary commands
# RUN /bin/bash -c "source /home/sung/.profile"

# RUN python3 /home/sung/generate.py
# RUN sudo service supervisor stop

# # Run flink commands
COPY flink_inventory/flink-core-1.16.3.jar /home/sung/flink-1.16.3/lib/
COPY flink_inventory/flink-streaming-java-1.16.3.jar /home/sung/flink-1.16.3/lib/
COPY flink_inventory/flink-table-api-java-bridge-1.16.3.jar /home/sung/flink-1.16.3/lib/
COPY flink_inventory/flink-java-1.16.3.jar /home/sung/flink-1.16.3/lib/
COPY flink_inventory/flink-runtime-web-1.16.3.jar  /home/sung/flink-1.16.3/lib/



# Expose any necessary ports
EXPOSE 8081
EXPOSE 22

# Command to run Kafka when the container starts
CMD ["-c", "python3 /home/sung/generate.py; echo '1234' | sudo -S service supervisor start"]


