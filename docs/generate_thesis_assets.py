from pathlib import Path

import matplotlib
import matplotlib.pyplot as plt
from matplotlib.patches import Ellipse, FancyBboxPatch


matplotlib.rcParams["font.sans-serif"] = [
    "Microsoft YaHei",
    "SimHei",
    "Arial Unicode MS",
    "DejaVu Sans",
]
matplotlib.rcParams["axes.unicode_minus"] = False


OUTPUT_DIR = Path("docs/thesis-assets")


def setup_axes(figsize=(14, 8), title=""):
    fig, ax = plt.subplots(figsize=figsize)
    ax.set_xlim(0, 1)
    ax.set_ylim(0, 1)
    ax.axis("off")
    if title:
        ax.text(0.5, 0.965, title, ha="center", va="center", fontsize=20, fontweight="bold")
    return fig, ax


def add_box(ax, x, y, w, h, text, fc="#EAF4FF", ec="#2F5D8A", fontsize=11, lw=1.6):
    box = FancyBboxPatch(
        (x, y),
        w,
        h,
        boxstyle="round,pad=0.01,rounding_size=0.02",
        linewidth=lw,
        edgecolor=ec,
        facecolor=fc,
    )
    ax.add_patch(box)
    ax.text(x + w / 2, y + h / 2, text, ha="center", va="center", fontsize=fontsize, wrap=True)


def add_ellipse(ax, x, y, w, h, text, fc="#FFF3DA", ec="#B26A00", fontsize=11, lw=1.6):
    e = Ellipse((x, y), w, h, linewidth=lw, edgecolor=ec, facecolor=fc)
    ax.add_patch(e)
    ax.text(x, y, text, ha="center", va="center", fontsize=fontsize, wrap=True)


def add_arrow(ax, x1, y1, x2, y2, text=None, fontsize=9, color="#3A3A3A", lw=1.5, dashed=False):
    ax.annotate(
        "",
        xy=(x2, y2),
        xytext=(x1, y1),
        arrowprops=dict(
            arrowstyle="->",
            lw=lw,
            color=color,
            linestyle="--" if dashed else "-",
        ),
    )
    if text:
        ax.text((x1 + x2) / 2, (y1 + y2) / 2 + 0.015, text, fontsize=fontsize, ha="center", color=color)


def add_polyline_arrow(ax, points, color="#3A3A3A", lw=1.5, dashed=False):
    if len(points) < 2:
        return
    for i in range(len(points) - 2):
        x1, y1 = points[i]
        x2, y2 = points[i + 1]
        ax.plot(
            [x1, x2],
            [y1, y2],
            color=color,
            lw=lw,
            linestyle="--" if dashed else "-",
            solid_capstyle="round",
        )
    x1, y1 = points[-2]
    x2, y2 = points[-1]
    add_arrow(ax, x1, y1, x2, y2, color=color, lw=lw, dashed=dashed)


def save_fig(fig, name):
    OUTPUT_DIR.mkdir(parents=True, exist_ok=True)
    out = OUTPUT_DIR / name
    fig.tight_layout()
    fig.savefig(out, dpi=240, bbox_inches="tight")
    plt.close(fig)
    return out


def draw_use_case():
    fig, ax = setup_axes(figsize=(15, 9), title="农场能源管理系统用例图")

    # system boundary
    add_box(
        ax,
        0.18,
        0.08,
        0.78,
        0.84,
        "",
        fc="#FCFCFC",
        ec="#6E6E6E",
        fontsize=10,
        lw=1.8,
    )
    ax.text(0.21, 0.89, "系统边界：农场能源管理系统", fontsize=12, color="#4A4A4A")

    # actors
    actors = {
        "系统管理员": {"xy": (0.03, 0.76), "color": "#B0433C", "lane_x": 0.235},
        "农场经理": {"xy": (0.03, 0.46), "color": "#2F5D8A", "lane_x": 0.215},
        "查看员": {"xy": (0.03, 0.16), "color": "#2E7D32", "lane_x": 0.195},
    }
    for name, cfg in actors.items():
        x, y = cfg["xy"]
        add_box(ax, x, y, 0.13, 0.08, name, fc="#EEF6EE", ec=cfg["color"], fontsize=10)

    # use-cases
    ellipse_w = 0.24
    ellipse_h = 0.09
    use_cases = {
        "登录认证": (0.43, 0.82),
        "能源监测与数据采集": (0.43, 0.66),
        "能源优化与节能管理": (0.43, 0.50),
        "碳排放监测与减排": (0.43, 0.34),
        "预测与供给计划": (0.43, 0.18),
        "报告生成与下载": (0.75, 0.74),
        "用户与角色管理": (0.75, 0.42),
        "审计日志查询": (0.75, 0.26),
    }
    for name, (cx, cy) in use_cases.items():
        add_ellipse(ax, cx, cy, ellipse_w, ellipse_h, name)

    def link(actor_name, use_case_name):
        actor_cfg = actors[actor_name]
        actor_right = actor_cfg["xy"][0] + 0.13
        actor_y = actor_cfg["xy"][1] + 0.04
        lane_x = actor_cfg["lane_x"]

        target_cx, target_cy = use_cases[use_case_name]
        target_left = target_cx - ellipse_w / 2
        points = [
            (actor_right, actor_y),
            (lane_x, actor_y),
            (lane_x, target_cy),
            (target_left, target_cy),
        ]
        add_polyline_arrow(ax, points, color=actor_cfg["color"], lw=1.35)

    # admin
    link("系统管理员", "登录认证")
    link("系统管理员", "用户与角色管理")
    link("系统管理员", "审计日志查询")
    link("系统管理员", "报告生成与下载")
    # manager
    link("农场经理", "登录认证")
    link("农场经理", "能源监测与数据采集")
    link("农场经理", "能源优化与节能管理")
    link("农场经理", "碳排放监测与减排")
    link("农场经理", "预测与供给计划")
    link("农场经理", "报告生成与下载")
    # viewer
    link("查看员", "登录认证")
    link("查看员", "能源监测与数据采集")
    link("查看员", "碳排放监测与减排")
    link("查看员", "报告生成与下载")

    # legend
    ax.text(0.20, 0.03, "红线：系统管理员  蓝线：农场经理  绿线：查看员", fontsize=10, color="#404040")
    return save_fig(fig, "use_case_diagram.png")


def draw_architecture():
    fig, ax = setup_axes(title="系统总体架构图（Vue + Spring Boot + MySQL）")

    add_box(ax, 0.08, 0.82, 0.84, 0.10, "表示层：Vue 3 + Ant Design Vue + ECharts（浏览器）", fc="#EAF4FF")
    add_box(ax, 0.08, 0.67, 0.84, 0.10, "网关层：Axios + RESTful API + Token 鉴权", fc="#F5F9E8", ec="#5E8C31")

    add_box(ax, 0.08, 0.47, 0.26, 0.14, "控制层\nController", fc="#FFF5E9", ec="#B36D00")
    add_box(ax, 0.37, 0.47, 0.26, 0.14, "业务层\nService", fc="#FFF5E9", ec="#B36D00")
    add_box(ax, 0.66, 0.47, 0.26, 0.14, "安全层\nAuthInterceptor", fc="#FFF5E9", ec="#B36D00")

    add_box(ax, 0.08, 0.28, 0.40, 0.12, "数据访问层：Spring Data JPA + JdbcTemplate", fc="#F0ECFF", ec="#6C4EB5")
    add_box(ax, 0.52, 0.28, 0.40, 0.12, "文件服务层：OpenPDF + 报告目录", fc="#F0ECFF", ec="#6C4EB5")

    add_box(ax, 0.08, 0.12, 0.40, 0.10, "MySQL：监测/优化/碳排/预测/用户/会话/日志", fc="#E8F7F6", ec="#1E7F7A")
    add_box(ax, 0.52, 0.12, 0.40, 0.10, "业务数据源：光伏、风电、生物质与用能设备", fc="#E8F7F6", ec="#1E7F7A")

    # vertical main lines
    add_arrow(ax, 0.50, 0.82, 0.50, 0.77)
    add_arrow(ax, 0.50, 0.67, 0.21, 0.61)
    add_arrow(ax, 0.50, 0.67, 0.50, 0.61)
    add_arrow(ax, 0.50, 0.67, 0.79, 0.61)

    add_arrow(ax, 0.21, 0.47, 0.28, 0.40)
    add_arrow(ax, 0.50, 0.47, 0.28, 0.40)
    add_arrow(ax, 0.79, 0.47, 0.72, 0.40)

    add_arrow(ax, 0.28, 0.28, 0.28, 0.22)
    add_arrow(ax, 0.72, 0.28, 0.72, 0.22)
    add_arrow(ax, 0.72, 0.12, 0.72, 0.28)
    return save_fig(fig, "architecture_diagram.png")


def draw_er():
    fig, ax = setup_axes(figsize=(16, 10), title="E-R 数据库设计图（核心表）")

    add_box(ax, 0.07, 0.74, 0.22, 0.16, "sys_users\nPK id\nusername\npassword_hash\nrole,status", fc="#E8F7E8", ec="#2E7D32", fontsize=9)
    add_box(ax, 0.39, 0.74, 0.22, 0.16, "user_sessions\nPK id\nFK user_id\ntoken\nexpires_at", fc="#E8F7E8", ec="#2E7D32", fontsize=9)
    add_box(ax, 0.71, 0.74, 0.22, 0.16, "operation_logs\nPK id\nmodule\naction\nusername,role", fc="#E8F7E8", ec="#2E7D32", fontsize=9)

    add_box(ax, 0.07, 0.48, 0.22, 0.16, "energy_devices\nPK id\ndevice_name\ndevice_type\ncapacity,status", fc="#EAF4FF", ec="#2F5D8A", fontsize=9)
    add_box(ax, 0.39, 0.48, 0.22, 0.16, "energy_monitoring\nPK id\nFK device_id\ntimestamp\nenergy_generated/consumed", fc="#EAF4FF", ec="#2F5D8A", fontsize=9)
    add_box(ax, 0.71, 0.48, 0.22, 0.16, "energy_recommendations\nPK id\nrecommendation_type\npriority,status\npotential_savings", fc="#EAF4FF", ec="#2F5D8A", fontsize=9)

    add_box(ax, 0.07, 0.22, 0.22, 0.16, "carbon_emissions\nPK id\ntimestamp\nenergy_source\ncarbon_emission", fc="#F0ECFF", ec="#6C4EB5", fontsize=9)
    add_box(ax, 0.39, 0.22, 0.22, 0.16, "energy_forecasts\nPK id\nforecast_date\npredicted_generation\npredicted_consumption", fc="#F0ECFF", ec="#6C4EB5", fontsize=9)
    add_box(ax, 0.71, 0.22, 0.22, 0.16, "reports\nPK id\nreport_type\nreport_period\nfile_path,status", fc="#F0ECFF", ec="#6C4EB5", fontsize=9)

    # FK relationships (solid)
    add_arrow(ax, 0.29, 0.82, 0.39, 0.82, "1:N", color="#2E7D32", lw=1.6)
    add_arrow(ax, 0.29, 0.56, 0.39, 0.56, "1:N", color="#2F5D8A", lw=1.6)

    # Business aggregation relations (dashed routed lines)
    add_polyline_arrow(ax, [(0.61, 0.56), (0.66, 0.56), (0.66, 0.30), (0.71, 0.30)], color="#6C4EB5", lw=1.3, dashed=True)
    add_polyline_arrow(ax, [(0.29, 0.30), (0.66, 0.30), (0.71, 0.30)], color="#6C4EB5", lw=1.3, dashed=True)
    add_polyline_arrow(ax, [(0.61, 0.30), (0.71, 0.30)], color="#6C4EB5", lw=1.3, dashed=True)
    add_polyline_arrow(ax, [(0.18, 0.74), (0.18, 0.66), (0.82, 0.66), (0.82, 0.64)], color="#8A5D00", lw=1.2, dashed=True)

    ax.text(0.07, 0.08, "实线：外键关系    虚线：业务聚合关系（非外键）", fontsize=10, color="#444")
    return save_fig(fig, "er_diagram.png")


def draw_flow(title, filename, steps):
    fig, ax = setup_axes(figsize=(12, 10), title=title)
    x = 0.5
    w = 0.80
    h = 0.11
    y_points = [0.86, 0.69, 0.52, 0.35, 0.18]

    for idx, step in enumerate(steps):
        y = y_points[idx]
        add_box(ax, x - w / 2, y - h / 2, w, h, step, fc="#F6FBFF", ec="#2F5D8A", fontsize=12, lw=1.6)
        if idx < len(steps) - 1:
            next_y = y_points[idx + 1]
            add_arrow(ax, x, y - h / 2 - 0.01, x, next_y + h / 2 + 0.01, color="#2F5D8A", lw=1.5)
    return save_fig(fig, filename)


def main():
    outputs = []
    outputs.append(draw_use_case())
    outputs.append(draw_architecture())
    outputs.append(draw_er())
    outputs.append(
        draw_flow(
            "模块流程图：能源监测与数据采集",
            "flow_monitoring.png",
            [
                "设备登记与参数配置（光伏/风机/生物质）",
                "采集端上报发电量、耗电量、温湿度等实时数据",
                "后端校验设备状态并写入 energy_monitoring",
                "统计当日总发电/总耗电/平均效率",
                "前端仪表盘与图表实时展示，支持历史查询",
            ],
        )
    )
    outputs.append(
        draw_flow(
            "模块流程图：能源利用优化与节能管理",
            "flow_optimization.png",
            [
                "读取近 7 日监测数据并计算利用率、盈余量、平均效率",
                "根据阈值规则自动生成节能建议",
                "管理员/经理新增、编辑、更新建议执行状态",
                "系统按优先级展示建议并统计潜在节约量",
                "形成节能结果闭环并反馈到运维策略",
            ],
        )
    )
    outputs.append(
        draw_flow(
            "模块流程图：碳排放监测与减排措施",
            "flow_carbon.png",
            [
                "录入能源类型与用能量（solar/wind/grid 等）",
                "按排放因子自动计算 carbon_emission",
                "按日/周/月统计总排放并输出趋势",
                "识别高占比排放源并生成减排策略",
                "展示碳中和进度与可再生能源占比",
            ],
        )
    )
    outputs.append(
        draw_flow(
            "模块流程图：能源预测与供给计划",
            "flow_forecast.png",
            [
                "提取近 30 日历史发电与耗电序列",
                "执行趋势 + 季节系数预测得到未来需求",
                "生成日目标、峰值预案与最小储备计划",
                "支持人工调整并保存预测记录",
                "回填实际值后计算预测精度并持续优化",
            ],
        )
    )
    outputs.append(
        draw_flow(
            "模块流程图：报告生成与下载",
            "flow_report.png",
            [
                "用户选择报告类型与时间周期",
                "聚合能源、碳排放、优化建议三类数据",
                "OpenPDF 生成 PDF 并写入 reports 目录",
                "记录报告元数据到 reports 表",
                "鉴权后下载文件并在前端展示记录列表",
            ],
        )
    )

    print("Generated assets:")
    for p in outputs:
        print(p)


if __name__ == "__main__":
    main()
