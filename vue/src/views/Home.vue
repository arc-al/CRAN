<template>
  <div>
    <el-row>
      <el-col :span="12">
        <div id="main" style="width: 500px; height: 600px"></div>
      </el-col>
      <el-col :span="12">
        <div id="pai" style="width: 500px; height: 600px"></div>
      </el-col>
    </el-row>


  </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  name: "Home",
  data(){
    return  {

    }
  },
  mounted() {
    var chartDom = document.getElementById('main');
    var myChart = echarts.init(chartDom);
    var option;

    option = {
      xAxis: {
        type: 'category',
        data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          data: [150, 230, 224, 218, 135, 147, 260],
          type: 'bar'
        },
        {
          data: [150, 230, 224, 218, 135, 147, 260],
          type: 'line'
        }
      ]
    };

    myChart.setOption(option);


    var paichartDom = document.getElementById('pai');
    var paimyChart = echarts.init(paichartDom);
    var paioption;

    paioption = {
      legend: {
        top: 'bottom'
      },
      toolbox: {
        show: true,
        feature: {
          mark: { show: true },
          dataView: { show: true, readOnly: false },
          restore: { show: true },
          saveAsImage: { show: true }
        }
      },
      series: [
        {
          name: 'Nightingale Chart',
          type: 'pie',
          radius: [50, 250],
          center: ['50%', '50%'],
          roseType: 'area',
          itemStyle: {
            borderRadius: 8
          },
          data: [
            { value: 40, name: 'rose 1' },
            { value: 38, name: 'rose 2' },
            { value: 32, name: 'rose 3' },
            { value: 30, name: 'rose 4' },
            { value: 28, name: 'rose 5' },
            { value: 26, name: 'rose 6' },
            { value: 22, name: 'rose 7' },
            { value: 18, name: 'rose 8' }
          ]
        }
      ]
    };

    paimyChart.setOption(paioption);

  }
}
</script>

<style scoped>

</style>