<!-- Tìm kiếm bưu cục -->
<template>
    <div class="mt-3">
        <div class="card" >
            <div class="card-body">
              <h3 class="card-title h3 p-2">Tìm kiếm bưu cục</h3>
              <div class="col-6">
                <p><strong class="label">Mã phiếu gửi</strong> <span class="text-danger label">( Có thể tra nhiều bill bằng cách thêm dấu phẩy giữa các bill )</span></p>
                <form class="row">
                    <div class="col-8">
                        <input type="text" class="form-control" placeholder="VD: 12345,23455" required="">
                    </div>
                    <div class="col-4">
                        <button class="btn btn-success" type="submit">Tra cứu</button>
                    </div>
                </form>
              </div>
              <div class="col-6">

              </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: 'PostOfficeSearch',
    }
</script>
