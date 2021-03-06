package stan.androiddemo.UI

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import rx.Subscription
import rx.subscriptions.CompositeSubscription
import stan.androiddemo.R
import stan.androiddemo.project.petal.Config.Config
import stan.androiddemo.project.petal.Config.UserSingleton
import stan.androiddemo.tool.NetUtils
import stan.androiddemo.tool.SPUtils

/**
 * Created by stanhu on 11/8/2017.
 */
abstract class BasePetalFragment : Fragment() {
    protected var TAG = getTheTAG()

    protected abstract fun getTheTAG(): String
    var isLogin = false
    lateinit var mRootView: View

    //联网的授权字段 几乎所有的Fragment子类都有联网功能 故父类提供变量
    protected var mAuthorization: String? = null

    private var mCompositeSubscription: CompositeSubscription? = null
    fun getCompositeSubscription(): CompositeSubscription {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = CompositeSubscription()
        }

        return this.mCompositeSubscription!!
    }

    fun addSubscription(s: Subscription?) {
        if (s == null) {
            return
        }

        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = CompositeSubscription()
        }

        this.mCompositeSubscription!!.add(s)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isLogin = SPUtils.get(context, Config.ISLOGIN,false) as Boolean
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRootView = inflater!!.inflate(R.layout.fragment_base_recycler, container, false)
        return mRootView
    }


    override fun toString(): String {
        return javaClass.simpleName + " @" + Integer.toHexString(hashCode())
    }

    protected fun checkException(e: Throwable?) {
        NetUtils.checkHttpException(context, e, mRootView)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this.mCompositeSubscription != null){
            this.mCompositeSubscription = null
        }
    }
    fun toast(msg:String){
        Toast.makeText(context,msg, Toast.LENGTH_SHORT).show()
    }
}