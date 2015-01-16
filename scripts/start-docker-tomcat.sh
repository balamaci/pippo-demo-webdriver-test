#!/bin/bash

docker_output_dir="${DOCKER_OUTPUT_DIR}"
docker_build_file="${DOCKER_FILE_LOCATION}"
docker_image_name="${DOCKER_IMAGE_NAME}"
docker_tomcat_log_dir=${docker_output_dir}/log
docker_container_property_file=${docker_output_dir}/container.properties

mkdir -p ${docker_output_dir}
mkdir -p ${docker_tomcat_log_dir}

#cp ${docker_file} ${distribution_file} ${docker_build_dir}/

#image_id=$(docker build --rm -q=false ${docker_build_dir} | grep "Successfully built" | cut -d " " -f 3)
#echo ${image_id} > ${docker_output_dir}/docker_image.id

#rm -rf ${docker_build_dir}

tomcat_log="${docker_tomcat_log_dir}/catalina.$(date +%Y-%m-%d).log"

## starting the container
container_id=$(docker run ${docker_run_args} -v ${docker_tomcat_log_dir}:/opt/tomcat/logs -d ${docker_image_name})
echo "CONTAINER_ID=${container_id}"$'\r' > ${docker_container_property_file}

container_ip=$(docker inspect --format '{{.NetworkSettings.IPAddress}}' ${container_id})

echo -n "Waiting for tomcat to finish startup..."

# Waiting for Tomcat to start...
#while ! grep -i -q 'INFO: Server startup in' ${tomcat_log} ; do
#    sleep 5
#    echo -n "."
#done

#echo -n "Tomcat started"

echo "CONTAINER_IP=${container_ip}" >> ${docker_container_property_file}
