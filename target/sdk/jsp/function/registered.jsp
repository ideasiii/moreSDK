<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="container">
	<div class="row">
		<div class="col-sm-8 col-sm-offset-2">
			<!--      Wizard container        -->
			<div class="wizard-container">
				<div class="card wizard-card" data-color="green" id="wizard">
					<div class="wizard-header">
						<h3 class="wizard-title">
							<img
								src='<s:property value="contextPath"/>/images/sdk/more_logo1.png'>
						</h3>
						<h5>
							<a href='<s:url action="login" namespace="/function"/>'
								style="background-color: #900; color: #FFF; font-size: 0.8em;">&nbsp;【回登入頁面】&nbsp;</a>
						</h5>
					</div>
					<div class="wizard-navigation">
						<ul>
							<li><a href="#details" data-toggle="tab">註冊資料</a></li>

							<li><a href="#description" data-toggle="tab">會員資料同意書</a></li>
						</ul>
					</div>

					<div class="tab-content">

						<div class="tab-pane" id="details">
							<form id="dataStep1Form">
								<div class="row">
									<div>
										<div class="col-sm-12">
											<div class="form-group label-floating">
												<label class="control-label">Email Account</label> <input
													type="text" class="form-control" id="email" name="email">
											</div>
										</div>
									</div>
									<div>
										<div class="col-sm-6">
											<div class="form-group label-floating">
												<label class="control-label">Password (Minimum 8
													charcters allowed)</label> <input type="password"
													class="form-control" id="newPassword" name="newPassword">
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group label-floating">
												<label class="control-label">Password again</label> <input
													type="password" class="form-control" id="confirmPassword"
													name="confirmPassword">
											</div>
										</div>
										<div class="clearfix"></div>
									</div>
									<div>
										<div class="col-sm-6">
											<div class="form-group label-floating">
												<label class="control-label">Name</label> <input type="text"
													class="form-control" id="displayName" name="displayName">
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group label-floating">
												<label class="control-label">Company</label> <input
													type="text" class="form-control" id="company"
													name="company">
											</div>
										</div>
										<div class="clearfix"></div>
									</div>
									<div>
										<div class="col-sm-6">
											<div class="form-group label-floating">
												<label class="control-label">Phone (ex:00-12345678
													or 0912345678)</label> <input type="text" class="form-control"
													id="phone" name="phone">
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group label-floating">
												<label class="control-label">Purpose</label> <input
													type="text" class="form-control" id="purpose"
													name="purpose">
											</div>
										</div>
										<div class="clearfix"></div>
									</div>
									<div>
										<div class="col-sm-12">
											<div class="g-recaptcha"
												data-sitekey="6Le380YUAAAAALhDbgYnN4dQk3DgN2mBM6J-n03F"></div>
										</div>
									</div>
								</div>
							</form>
						</div>

						<div class="tab-pane" id="description">
							<form id="dataStep2Form">
								<div class="row">

									<div class="col-sm-12">
										<div class="form-group">
											<div>
												<s:if test='"TW".equals(language)'>
													<p align="center" style="font-size: 1.2em;">
														<strong>財團法人資訊工業策進會</strong><strong> </strong><br> <strong>蒐集個人資料告知事項暨個人資料提供同意書</strong><strong>
														</strong>
													</p>
													<p style="text-align: right">版本: P-V4-DSI</p>
													<p>
														<strong>財團法人資訊工業策進會</strong><strong>(</strong><strong>下稱本會</strong><strong>)</strong>為遵守個人資料保護法令及本會個人資料保護政策、規章，於向您蒐集個人資料前，依法向您告知下列事項，敬請詳閱。
													</p>
													<ol>
														<li>蒐集目的及類別</li>
													</ol>
													<p>
														本會因辦理或執行業務、活動、計畫、提供服務及供本會用於內部行政管理、陳報主管機關或其他合於本會捐助章程所定業務、寄送本會或產業相關活動訊息之蒐集目的，而需獲取您下列個人資料類別：姓名、聯絡方式(如電話號碼、職稱、電子信箱、居住或工作地址等)、身分證統一編號，或其他得以直接或間接識別您個人之資料。
														<br> ※您日後如不願再收到本會所寄送之行銷訊息，可於收到前述訊息時，直接點選訊息內拒絕接受之連結。
													</p>
													<ol>
														<li>個人資料利用之期間、地區、對象及方式</li>
													</ol>
													<p>   
														除涉及國際業務或活動外，您的個人資料僅供本會於中華民國領域、在前述蒐集目的之必要範圍內，以合理方式利用至蒐集目的消失為止。
													</p>
													<ol>
														<li>當事人權利</li>
													</ol>
													<p>您可依前述業務、活動所定規則或依本會網站（https://www.iii.org.tw/）「個資當事人行使權利專頁」公告方式向本會行使下列權利：
													</p>
													<ol>
														<li>查詢或請求閱覽。</li>
														<li>請求製給複製本。</li>
														<li>請求補充或更正。</li>
														<li>請求停止蒐集、處理及利用。</li>
														<li>請求刪除您的個人資料。</li>
													</ol>
													<ol>
														<li>不提供個人資料之權益影響</li>
													</ol>
													<p>若您未提供正確或不提供個人資料，本會將無法為您提供蒐集目的之相關服務。</p>
													<ol>
														<li>您瞭解此一同意書符合個人資料保護法及相關法規之要求，且同意本會留存此同意書，供日後取出查驗。</li>
													</ol>
													<p>&nbsp;</p>
													<p>
														<strong>個人資料之同意提供：</strong><strong> </strong>
													</p>
													<ol>
														<li>本人已充分獲知且已瞭解上述貴會告知事項。</li>
														<li>本人同意貴會於所列蒐集目的之必要範圍內，蒐集、處理及利用本人之個人資料。</li>
													</ol>
												</s:if>
												<s:if test='"US".equals(language)'>
													<p style="text-align: center">
														<strong>Notification and Letter of Consent for
															Collection, Processing and Use of Personal Information</strong>
													</p>

													<p style="text-align: right">Version: P-V4-DSI</p>

													<p>
														According to the R.O.C. Personal Information Protection
														Act and the internal policy/regulations of<strong>
															Institute for Information Industry (hereinafter referred
															to as &ldquo;III&rdquo;), III </strong>is obligated to make the
														following notification known to you before you provide
														personal information to III. Please read it carefully.
													</p>

													<ul>
														<li><strong>1.Purposes and Categories of
																Personal Information to be Collected</strong></li>
													</ul>

													<p style="margin-left: 18.0pt">For the purpose of
														carrying out III&rsquo;s business, activity or project,
														the purpose of providing service,internal administrative
														management or reporting to the competent authority, the
														purpose complying with the Endowment, or the purpose of
														sending III&rsquo;s or industry relevant information, III
														needs you to provide these categories of personal
														information: name, contact information (such as phone
														number, title, e-mail address, residential or office
														address), ID card number, or other information which may
														be used to identify you directly or indirectly.</p>

													<p style="margin-left: 18.0pt">Note: You may choose, at
														any time in the future, not to receive any promotional
														information from III by clicking the link provided in the
														promotional message.</p>

													<ul>
														<li><strong>2.Time Period, Area, Target and
																Way of the Use of Personal Information</strong></li>
													</ul>

													<p style="margin-left: 18.0pt">Unless the purposes of
														use relating to international businesses or activities,
														your personal information will be used solely by III in a
														reasonable way in the territory of the Republic of China
														to the extent necessary to implement the purposes of
														collection until the purposes of collection prescribed
														above is fulfilled.</p>

													<p style="margin-left: 21.25pt">&nbsp;</p>

													<ul>
														<li><strong>3.Your Rights with regard to
																Personal Information Provided</strong></li>
													</ul>

													<p style="margin-left: 18.0pt">
														You may exercise the following rights in pursuant to the
														rules of the activities/project or by submitting your
														inquiry/request on our website (<a
															href="http://www.iii.org.tw/">https://www.iii.org.tw/</a>)
														at &ldquo;Application for Exercising Rights with Regard to
														Personal Information&rdquo; page:
													</p>

													<ol>
														<li>any inquiry and request for a review of the
															personal information;</li>
														<li>any request to make duplications of the personal
															information;</li>
														<li>any request to supplement or correct the personal
															information;</li>
														<li>any request to discontinue collection, processing
															or use of the personal information; and</li>
														<li>any request to delete the personal information.</li>
													</ol>

													<p style="margin-left: 43.5pt">&nbsp;</p>

													<ul>
														<li><strong>4.The Influence on Your Rights
																and Interests while You Choose not to Provide Your
																Personal Information</strong></li>
													</ul>

													<p style="margin-left: 18.0pt">If you provide incorrect
														personal information or choose not to provide your
														personal information to III, III may not be able to
														provide you with services relating to the purposes
														prescribed above.</p>

													<p style="margin-left: 24.0pt">&nbsp;</p>

													<ul>
														<li>5.You understand that this document complies with
															the R.O.C. Personal Information Protection Act and
															relating regulations, and you agree that III keeps this
															document for further checking.</li>
													</ul>

													<p style="margin-left: 24.0pt">&nbsp;</p>

													<p>
														<strong>The Consent to Provide Personal
															Information:</strong>
													</p>

													<ol>
														<li>I have read and understood the above
															notification.</li>
														<li>I agreed that III may collect, process and use my
															personal information for the purposes of collection
															prescribed above.</li>
													</ol>
												</s:if>
												<div>
													<div></div>
													<div></div>
												</div>
											</div>
										</div>

										<div>
											<div class="col-sm-12">
												<div class="checkbox">
													<label> <input type="checkbox" id="agreeCheckBox"
														name="agreeCheckBox">
													</label> 我同意蒐集個人資料告知事項暨當事人同意書
												</div>
											</div>
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>
					<div class="wizard-footer">
						<div class="pull-right">
							<input type='button'
								class='btn btn-next btn-fill btn-danger btn-wd' name='next'
								value='Next' /> <input type='button'
								class='btn btn-finish btn-fill btn-danger btn-wd' name='finish'
								value='Finish' onclick="registeredMember();" />
						</div>
						<div class="pull-left">
							<input type='button'
								class='btn btn-previous btn-fill btn-default btn-wd'
								name='previous' value='Previous' />
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
			</div>
			<!-- wizard container -->
			<div class="copy_txt">Powered By iii MORE SDK © 2018</div>
		</div>
	</div>
</div>
<div id="modalDiv"></div>