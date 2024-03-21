<!-- Tra cứu vận chuyển -->
<template>
    <div class="mt-3">
        <div class="card">
          <div class="card-body">
              <h3 class="card-title h3 p-2">Tra cứu vận chuyển</h3>
              <div class="row">
                <div class="col-6">
                  <p class="d-flex justify-content-left"><strong class="label">Mã phiếu gửi</strong> <span class="text-danger label">( Có thể tra nhiều bill bằng cách thêm dấu phẩy giữa các bill )</span></p>
                  <form class="row">
                      <div class="d-flex justify-content-between">
                          <input type="text" class="form-control me-2 flex-grow-1" style="max-width: 70%;" placeholder="VD: 12345,23455" required="">
                          <button class="btn btn-success flex-grow-1" style="max-width: 28%; white-space: nowrap;" type="submit">Tra cứu</button>
                      </div>
                  </form>
                </div>
                <div class="col-6">
                  <img src="../assets/image/cargo-truck.png" alt="Tra cứu vận chuyển" class="img-fluid">
                </div>
              </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: 'ShippingLookup',
    }
</script>
