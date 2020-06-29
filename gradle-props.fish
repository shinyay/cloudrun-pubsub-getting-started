#!/usr/bin/env fish

function do_func
  argparse -n do_func 'h/help' 'i/id=' 'u/user=' 'p/password=' -- $argv
  or return 1

  if set -lq _flag_help
    echo "gradle-prop.fish -i/--id <DOCKER_ID> -u/user <DOCKER_USER> -p/password <DOCKER_PASSWORD>"
    return
  end

  set -lq _flag_id
  or set -l _flag_id "<DOCKER_ID>"

  set -lq _flag_user
  or set -l _flag_user "<DOCKER_USER>"

  set -lq _flag_password
  or set -l _flag_password "<DOCKER_PASSWORD>"

  echo "docker_project_id=$_flag_id" > gradle.properties
  echo "docker_username=$_flag_user" >> gradle.properties
  echo "docker_password=$_flag_password" >> gradle.properties
end

do_func $argv